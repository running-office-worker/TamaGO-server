package tamago.server.core.running.domain.aggregate

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.vo.RunningPlanId
import java.time.LocalDateTime

class RunningPlan(
    val id: RunningPlanId? = null,
    val userId: UserId,
    val goalDistance: Double,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            userId: UserId,
            goalDistance: Double,
        ): RunningPlan =
            RunningPlan(
                userId = userId,
                goalDistance = goalDistance,
            )
    }
}
