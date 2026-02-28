package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.RunningQueryUseCase
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import java.time.LocalDateTime

@Service
class RunningQueryService(
    private val runningPersistencePort: RunningPersistencePort,
) : RunningQueryUseCase {
    override fun getLastFinishedAt(userId: UserId): LocalDateTime? =
        runningPersistencePort.findLastByUserId(userId)?.finishedAt

    fun getMonthlyRunnings(
        userId: UserId,
        year: Int,
        month: Int,
    ): List<Running> = runningPersistencePort.findAllByUserIdAndMonth(userId, year, month)

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
}
