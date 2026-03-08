package tamago.server.core.running.infrastructure.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.vo.RunningPlanId
import tamago.server.core.running.infrastructure.entity.RunningPlanEntity

object RunningPlanMapper {
    fun toEntity(runningPlan: RunningPlan): RunningPlanEntity =
        RunningPlanEntity(
            id = runningPlan.id?.value,
            userId = runningPlan.userId.value,
            goalDistance = runningPlan.goalDistance,
        )

    fun toDomain(entity: RunningPlanEntity?): RunningPlan? {
        if (entity == null) return null

        return RunningPlan(
            id = entity.id?.let { RunningPlanId(it) },
            userId = UserId(entity.userId),
            goalDistance = entity.goalDistance,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
