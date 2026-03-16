package tamago.server.core.running

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.event.RunningCompletedEvent
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
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
import tamago.server.core.running.domain.vo.RunningPlanId

@Component
class RunningFacade(
    private val runningCommandService: RunningCommandService,
    private val runningQueryService: RunningQueryService,
    private val runningPlanQueryService: RunningPlanQueryService,
    private val runningPlanCommandService: RunningPlanCommandService,
    private val monsterCommandUseCase: MonsterCommandUseCase,
    private val monsterQueryUseCase: MonsterQueryUseCase,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun createRunningPlan(command: CreateRunningPlanCommandDto): RunningPlanId =
        runningPlanCommandService.create(command).id!!

    @Transactional
    fun saveRunningData(command: SaveRunningCommandDto): RunningFinishQueryDto {
        // runningPlan 존재 여부 검증
        runningPlanQueryService.getById(command.runningPlanId)

        val running = runningCommandService.save(command)
        val totalDistance = runningQueryService.getTotalDistance(command.userId)
        val totalDurationMinutes = runningQueryService.getTotalDurationMinutes(command.userId)

        publishRunningCompletedEvent(command, totalDistance, totalDurationMinutes)

        // Query로 XP 계산, Command로 저장 (CQRS 분리)
        val xpResult = monsterQueryUseCase.calculateEarnedXp(command.ownedMonsterId, command.distance)
        monsterCommandUseCase.addEarnedXp(command.ownedMonsterId, xpResult.earnedXp)

        return RunningFinishQueryDto.of(
            running = running,
            xpResult = xpResult,
            waypoints = command.waypoints.map { RunningFinishQueryDto.WaypointDto(it.latitude, it.longitude) },
        )
    }

    private fun publishRunningCompletedEvent(
        command: SaveRunningCommandDto,
        totalDistance: Double,
        totalDurationMinutes: Long,
    ) {
        val streak = runningQueryService.getRunningStreak(command.userId)

        applicationEventPublisher.publishEvent(
            RunningCompletedEvent(
                userId = command.userId,
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
