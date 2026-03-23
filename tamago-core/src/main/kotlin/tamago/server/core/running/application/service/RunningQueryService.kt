package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.RunningQuery
import tamago.server.core.running.RunningQueryUseCase
import tamago.server.core.running.application.exception.RunningNotFoundException
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.aggregate.RunningRoute
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.core.running.domain.port.outbound.RunningPlanPersistencePort
import tamago.server.core.running.domain.port.outbound.RunningRoutePersistencePort
import tamago.server.core.running.domain.vo.RunningId
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class RunningQueryService(
    private val runningPersistencePort: RunningPersistencePort,
    private val runningPlanPersistencePort: RunningPlanPersistencePort,
    private val runningRoutePersistencePort: RunningRoutePersistencePort,
) : RunningQueryUseCase {
    fun getRunning(
        runningId: RunningId,
        userId: UserId,
    ): Running =
        runningPersistencePort.findByIdAndUserId(runningId, userId)
            ?: throw RunningNotFoundException()

    fun getRunningRoute(runningId: RunningId): RunningRoute? = runningRoutePersistencePort.findByRunningId(runningId)

    override fun getLastFinishedAt(userId: UserId): LocalDateTime? =
        runningPersistencePort.findLastByUserId(userId)?.finishedAt

    override fun getLastMonsterNickname(userId: UserId): String? =
        runningPersistencePort.findLastMonsterNicknameByUserId(userId)

    fun getMonthlyRunnings(
        userId: UserId,
        year: Int,
        month: Int,
    ): List<Running> = runningPersistencePort.findAllByUserIdAndMonth(userId, year, month)

    fun findAllByUserId(userId: UserId): List<Running> = runningPersistencePort.findAllByUserId(userId)

    fun findAllRunningPlansByUserId(userId: UserId): List<RunningPlan> =
        runningPlanPersistencePort.findAllByUserId(userId)

    fun findAllDeletedByUserId(userId: UserId): List<Running> = runningPersistencePort.findAllDeletedByUserId(userId)

    fun findAllDeletedRunningPlansByUserId(userId: UserId): List<RunningPlan> =
        runningPlanPersistencePort.findAllDeletedByUserId(userId)

    fun getTotalDistance(userId: UserId): Double = runningPersistencePort.sumDistanceByUserId(userId)

    fun getTotalDurationMinutes(userId: UserId): Long = runningPersistencePort.sumDurationMinutesByUserId(userId)

    override fun getStatsByOwnedMonsterIds(
        userId: UserId,
        ownedMonsterIds: List<Long>,
    ): List<MonsterRunningStatsQueryDto> =
        runningPersistencePort.findStatsByUserIdAndOwnedMonsterIds(
            userId,
            ownedMonsterIds,
        )

    override fun getRunningStreak(userId: UserId): RunningQuery.Streak {
        val today = LocalDate.now()
        val since = today.minusDays(MAX_LOOKUP_DAYS).atStartOfDay()

        // 최근 N일 내 러닝 날짜를 최신순(DESC)으로 조회
        val runningDates = runningPersistencePort.findRunningDatesByUserId(userId, since)

        // 러닝 기록이 없으면 첫 실행으로 간주하여 0일 연속, N일 미러닝으로 반환
        if (runningDates.isEmpty()) {
            return RunningQuery.Streak(isFirstRun = true, streakDays = 0, inactiveDays = MAX_LOOKUP_DAYS.toInt())
        }

        // 러닝 기록이 있으면 연속 러닝일수와 마지막 러닝 이후 미러닝 일수를 계산하여 반환
        return RunningQuery.Streak(
            isFirstRun = false,
            streakDays = calculateStreakDays(runningDates, today),
            inactiveDays = calculateInactiveDays(runningDates.first(), today),
        )
    }

    /**
     * 연속 러닝일수를 계산한다.
     * 오늘 러닝했으면 오늘부터, 아니면 어제부터 역순으로 연속된 날짜를 카운트한다.
     * @param runningDates 최신순 정렬된 러닝 날짜 목록
     */
    private fun calculateStreakDays(
        runningDates: List<LocalDate>,
        today: LocalDate,
    ): Int {
        var streakDays = 0
        var expectedDate = if (runningDates.contains(today)) today else today.minusDays(1)

        for (date in runningDates) {
            if (date == expectedDate) {
                streakDays++
                expectedDate = expectedDate.minusDays(1)
            } else if (date.isBefore(expectedDate)) {
                break
            }
        }

        return streakDays
    }

    /**
     * 마지막 러닝일로부터 오늘까지의 미러닝 일수를 계산한다.
     */
    private fun calculateInactiveDays(
        lastRunDate: LocalDate,
        today: LocalDate,
    ): Int = ChronoUnit.DAYS.between(lastRunDate, today).toInt()

    companion object {
        private const val MAX_LOOKUP_DAYS = 60L
    }
}
