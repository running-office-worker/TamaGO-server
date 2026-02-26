package tamago.server.core.running.domain.port.outbound

import tamago.server.core.running.domain.aggregate.RunningRoute

interface RunningRoutePersistencePort {
    fun save(runningRoute: RunningRoute): RunningRoute
}
