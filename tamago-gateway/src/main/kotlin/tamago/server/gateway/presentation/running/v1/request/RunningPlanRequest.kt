package tamago.server.gateway.presentation.running.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Positive
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.port.inbound.command.CreateRunningPlanCommandDto

@Schema(description = "러닝 계획 생성 바디")
data class RunningPlanRequest(
    @field:Schema(description = "목표 거리 (km)", example = "5.0", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:Positive(message = "목표 거리는 0보다 커야 합니다")
    val goalDistance: Double,
)

fun RunningPlanRequest.toCommand(userId: UserId) =
    CreateRunningPlanCommandDto(
        userId = userId,
        goalDistance = goalDistance,
    )
