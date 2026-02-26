package tamago.server.core.running.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running

interface RunningPersistencePort {
    fun save(running: Running): Running

    fun findAllByUserIdAndMonth(
        userId: UserId,
        year: Int,
        month: Int,
    ): List<Running>

    fun sumDistanceByUserId(userId: UserId): Double

    fun sumDurationMinutesByUserId(userId: UserId): Long
}
