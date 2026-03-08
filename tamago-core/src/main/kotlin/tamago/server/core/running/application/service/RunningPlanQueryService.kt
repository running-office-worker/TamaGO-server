package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.running.application.exception.RunningPlanNotFoundException
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.port.outbound.RunningPlanPersistencePort
import tamago.server.core.running.domain.vo.RunningPlanId

@Service
class RunningPlanQueryService(
    private val runningPlanPersistencePort: RunningPlanPersistencePort,
) {
    fun getById(id: RunningPlanId): RunningPlan =
        runningPlanPersistencePort.findById(id) ?: throw RunningPlanNotFoundException()
}
