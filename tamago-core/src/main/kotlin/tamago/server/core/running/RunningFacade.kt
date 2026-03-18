package tamago.server.core.running

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.event.RunningCompletedEvent
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.running.application.service.RunningCommandService
import tamago.server.core.running.application.service.RunningPlanCommandService
import tamago.server.core.running.application.service.RunningPlanQueryService
import tamago.server.core.running.application.service.RunningQueryService
import tamago.server.core.running.domain.port.inbound.command.CreateRunningPlanCommandDto
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import tamago.server.core.running.domain.port.inbound.query.MonthlyRunningQueryDto
import tamago.server.core.running.domain.port.inbound.query.RunningFinishQueryDto
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.domain.vo.RunningPlanId

@Component
class RunningFacade(
    private val runningCommandService: RunningCommandService,
    private val runningQueryService: RunningQueryService,
    private val runningPlanQueryService: RunningPlanQueryService,
    private val runningPlanCommandService: RunningPlanCommandService,
    private val monsterQueryUseCase: MonsterQueryUseCase,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun makeRunningPlan(command: CreateRunningPlanCommandDto): RunningPlanId =
        runningPlanCommandService.create(command).id!!

    @Transactional
    fun finishRun(command: SaveRunningCommandDto) =
        saveRunIfPlanned(command)
            .also { publishRunningCompletedEvent(command) }

    private fun saveRunIfPlanned(command: SaveRunningCommandDto): RunningId {
        runningPlanQueryService.get(command.runningPlanId, command.userId)
        return runningCommandService.save(command)
    }

    private fun publishRunningCompletedEvent(command: SaveRunningCommandDto) {
        val totalDistance = runningQueryService.getTotalDistance(command.userId)
        val totalDurationMinutes = runningQueryService.getTotalDurationMinutes(command.userId)
        val streak = runningQueryService.getRunningStreak(command.userId)

        applicationEventPublisher.publishEvent(
            RunningCompletedEvent(
                userId = command.userId,
                ownedMonsterId = command.ownedMonsterId,
                startedAt = command.startedAt,
                distance = command.distance,
                totalDistance = totalDistance,
                totalDurationMinutes = totalDurationMinutes,
                isFirstRun = streak.isFirstRun,
                streakDays = streak.streakDays,
                inactiveDays = streak.inactiveDays,
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getRunningResult(
        runningId: RunningId,
        userId: UserId,
    ): RunningFinishQueryDto {
        val running = runningQueryService.getRunning(runningId, userId)
        val runningRoute = runningQueryService.getRunningRoute(runningId)

        // LineString 좌표 → WaypointDto 변환
        val waypoints =
            runningRoute?.route?.let { lineString ->
                lineString.coordinates.map { coordinate ->
                    RunningFinishQueryDto.WaypointDto(
                        latitude = coordinate.y,
                        longitude = coordinate.x,
                    )
                }
            } ?: emptyList()

        val xpResult = monsterQueryUseCase.calculateEarnedXp(running.ownedMonsterId, running.distance ?: 0.0)

        return RunningFinishQueryDto.of(
            running = running,
            xpResult = xpResult,
            waypoints = waypoints,
        )
    }

    @Transactional(readOnly = true)
    fun getStatsByOwnedMonsterIds(
        userId: UserId,
        ownedMonsterIds: List<Long>,
    ): List<MonsterRunningStatsQueryDto> = runningQueryService.getStatsByOwnedMonsterIds(userId, ownedMonsterIds)

    @Transactional(readOnly = true)
    fun getMonthlyRunningData(
        userId: UserId,
        year: Int,
        month: Int,
    ): MonthlyRunningQueryDto {
        val runnings = runningQueryService.getMonthlyRunnings(userId, year, month)

        // 날짜별 그룹핑
        val dailyRunnings =
            runnings
                .groupBy { it.startedAt!!.toLocalDate() }
                .entries
                .sortedBy { it.key }
                .map { (date, runs) ->
                    val sortedRuns = runs.sortedBy { it.startedAt }
                    MonthlyRunningQueryDto.DailyRunningDto(
                        date = date,
                        runs = sortedRuns.map { running -> MonthlyRunningQueryDto.RunDetailDto.from(running) },
                        lastRunOwnedMonsterId = sortedRuns.last().ownedMonsterId.value,
                    )
                }

        return MonthlyRunningQueryDto(
            dailyRunnings = dailyRunnings,
            summary = MonthlyRunningQueryDto.MonthlySummaryDto.from(runnings),
        )
    }
}
