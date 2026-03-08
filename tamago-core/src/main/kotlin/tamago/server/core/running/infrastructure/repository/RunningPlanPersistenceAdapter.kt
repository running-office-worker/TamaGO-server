package tamago.server.core.running.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.port.outbound.RunningPlanPersistencePort
import tamago.server.core.running.domain.vo.RunningPlanId
import tamago.server.core.running.infrastructure.mapper.RunningPlanMapper

@Repository
class RunningPlanPersistenceAdapter(
    private val runningPlanJpaRepository: RunningPlanJpaRepository,
) : RunningPlanPersistencePort {
    override fun save(runningPlan: RunningPlan): RunningPlan =
        RunningPlanMapper.toDomain(runningPlanJpaRepository.save(RunningPlanMapper.toEntity(runningPlan)))!!

    override fun findById(id: RunningPlanId): RunningPlan? =
        runningPlanJpaRepository
            .findById(id.value)
            .map { RunningPlanMapper.toDomain(it) }
            .orElse(null)
}
