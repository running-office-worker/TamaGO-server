package tamago.server.core.running.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import java.time.LocalDate
import java.time.LocalDateTime

interface RunningPersistencePort {
    fun save(running: Running): Running

    fun findAllByUserIdAndMonth(
        userId: UserId,
        year: Int,
        month: Int,
    ): List<Running>

    fun findLastByUserId(userId: UserId): Running?

    fun sumDistanceByUserId(userId: UserId): Double

    fun sumDurationMinutesByUserId(userId: UserId): Long

    fun findStatsByUserIdAndOwnedMonsterIds(
        userId: UserId,
        ownedMonsterIds: List<Long>,
    ): List<MonsterRunningStatsQueryDto>

    fun findRunningDatesByUserId(
        userId: UserId,
        since: LocalDateTime,
    ): List<LocalDate>
}
