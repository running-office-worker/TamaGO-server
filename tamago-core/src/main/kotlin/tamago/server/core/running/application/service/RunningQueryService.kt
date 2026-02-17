package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.RunningQueryUseCase
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort

@Service
class RunningQueryService(
    private val runningPersistencePort: RunningPersistencePort,
) : RunningQueryUseCase {

    fun getMonthlyRunnings(userId: UserId, year: Int, month: Int): List<Running> =
        runningPersistencePort.findAllByUserIdAndMonth(userId, year, month)

    fun getTotalDistance(userId: UserId): Double =
        runningPersistencePort.sumDistanceByUserId(userId)

    fun getTotalDurationMinutes(userId: UserId): Long =
        runningPersistencePort.sumDurationMinutesByUserId(userId)
}
