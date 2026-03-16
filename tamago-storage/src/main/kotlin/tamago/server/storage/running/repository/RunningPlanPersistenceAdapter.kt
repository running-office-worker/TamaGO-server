package tamago.server.storage.running.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.port.outbound.RunningPlanPersistencePort
import tamago.server.core.running.domain.vo.RunningPlanId
import tamago.server.storage.running.mapper.RunningPlanMapper

@Repository
class RunningPlanPersistenceAdapter(
    private val runningPlanJpaRepository: RunningPlanJpaRepository,
) : RunningPlanPersistencePort {
    override fun save(runningPlan: RunningPlan): RunningPlan =
        RunningPlanMapper.toDomain(runningPlanJpaRepository.save(RunningPlanMapper.toEntity(runningPlan)))!!

    override fun findById(id: RunningPlanId): RunningPlan? =
        runningPlanJpaRepository.findByIdOrNull(id.value)?.let { RunningPlanMapper.toDomain(it) }
}
