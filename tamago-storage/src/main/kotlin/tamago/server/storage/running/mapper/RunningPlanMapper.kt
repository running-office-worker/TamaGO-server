package tamago.server.storage.running.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.vo.RunningPlanId
import tamago.server.storage.running.entity.RunningPlanEntity

object RunningPlanMapper {
    fun toEntity(domain: RunningPlan): RunningPlanEntity {
        val entity =
            RunningPlanEntity(
                id = domain.id?.value,
                userId = domain.userId.value,
                goalDistance = domain.goalDistance,
            )
        entity.deletedAt = domain.deletedAt
        return entity
    }

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
