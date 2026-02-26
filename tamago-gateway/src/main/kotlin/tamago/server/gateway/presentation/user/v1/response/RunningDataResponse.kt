package tamago.server.gateway.presentation.user.v1.response

import io.swagger.v3.oas.annotations.media.Schema

data class RunningDataResponse(
    @field:Schema(description = "누적 러닝 거리 (km)", example = "42.5", requiredMode = Schema.RequiredMode.REQUIRED)
    val totalKilo: Double,
    @field:Schema(description = "러닝 시작 후 경과 일수", example = "7", requiredMode = Schema.RequiredMode.REQUIRED)
    val runningDays: Long,
)
