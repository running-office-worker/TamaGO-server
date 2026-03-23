package tamago.server.core.running.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.vo.RunningPlanId

interface RunningPlanPersistencePort {
    fun save(runningPlan: RunningPlan): RunningPlan

    fun findByIdAndUserId(
        id: RunningPlanId,
        userId: UserId,
    ): RunningPlan?

    fun findAllByUserId(userId: UserId): List<RunningPlan>

    fun findAllDeletedByUserId(userId: UserId): List<RunningPlan>
}
