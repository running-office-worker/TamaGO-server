package tamago.server.core.running.domain.port.outbound

import tamago.server.core.running.domain.aggregate.RunningRoute
import tamago.server.core.running.domain.vo.RunningId

interface RunningRoutePersistencePort {
    fun save(runningRoute: RunningRoute): RunningRoute

    fun findByRunningId(runningId: RunningId): RunningRoute?

    fun hardDeleteAllByRunningIds(runningIds: List<RunningId>)
}
