package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.vo.RunningPlanId

@Schema(description = "러닝 계획 응답")
data class RunningPlanResponse(
    @field:Schema(description = "러닝 계획 ID", example = "1")
    val runningPlanId: Long,
) {
    companion object {
        fun from(runningPlanId: RunningPlanId): RunningPlanResponse =
            RunningPlanResponse(
                runningPlanId = runningPlanId.value,
            )
    }
}
