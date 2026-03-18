package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.vo.RunningId

@Schema(description = "러닝 저장 응답")
data class RunningResponse(
    @field:Schema(description = "러닝 기록 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val runningId: Long,
) {
    companion object {
        fun from(runningId: RunningId): RunningResponse =
            RunningResponse(
                runningId = runningId.value,
            )
    }
}
