package tamago.server.storage.running.repository

import org.springframework.stereotype.Repository
import tamago.server.core.running.domain.aggregate.RunningRoute
import tamago.server.core.running.domain.port.outbound.RunningRoutePersistencePort
import tamago.server.storage.running.mapper.RunningRouteMapper

@Repository
class RunningRoutePersistenceAdapter(
    private val runningRouteJpaRepository: RunningRouteJpaRepository,
) : RunningRoutePersistencePort {
    override fun save(runningRoute: RunningRoute): RunningRoute =
        RunningRouteMapper.toDomain(runningRouteJpaRepository.save(RunningRouteMapper.toEntity(runningRoute)))!!
}
