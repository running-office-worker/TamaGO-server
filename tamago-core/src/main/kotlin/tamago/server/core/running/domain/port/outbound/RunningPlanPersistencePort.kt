package tamago.server.core.running.domain.port.outbound

import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.vo.RunningPlanId

interface RunningPlanPersistencePort {
    fun save(runningPlan: RunningPlan): RunningPlan

    fun findById(id: RunningPlanId): RunningPlan?
}
