package tamago.server.core.running.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.core.running.infrastructure.mapper.RunningMapper

@Repository
class RunningPersistenceAdapter(
    private val runningJpaRepository: RunningJpaRepository,
) : RunningPersistencePort {

    override fun save(running: Running): Running =
        RunningMapper.toDomain(runningJpaRepository.save(RunningMapper.toEntity(running)))!!
}
