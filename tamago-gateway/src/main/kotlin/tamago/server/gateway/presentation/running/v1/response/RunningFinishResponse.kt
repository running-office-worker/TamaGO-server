package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.port.inbound.query.RunningFinishQueryDto

@Schema(description = "러닝 종료 응답")
data class RunningFinishResponse(
    @field:Schema(description = "페이스 (초/km)", example = "360", requiredMode = Schema.RequiredMode.REQUIRED)
    val pace: Int,
    @field:Schema(description = "케이던스 (spm)", example = "170", requiredMode = Schema.RequiredMode.REQUIRED)
    val cadence: Int,
    @field:Schema(description = "경과 시간 (초)", example = "2700", requiredMode = Schema.RequiredMode.REQUIRED)
    val elapsedTime: Int,
    @field:Schema(description = "총 소모 칼로리 (kcal)", example = "350", requiredMode = Schema.RequiredMode.REQUIRED)
    val totalCalories: Int,
) {
    companion object {
        fun from(dto: RunningFinishQueryDto) =
            RunningFinishResponse(
                pace = dto.pace,
                cadence = dto.cadence,
                elapsedTime = dto.elapsedTime,
                totalCalories = dto.totalCalories,
            )
    }
}
