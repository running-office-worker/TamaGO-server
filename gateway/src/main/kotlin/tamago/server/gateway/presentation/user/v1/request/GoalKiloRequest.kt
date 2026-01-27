package tamago.server.gateway.presentation.user.v1.request

import io.swagger.v3.oas.annotations.media.Schema

data class GoalKiloRequest(
    @field:Schema(description = "하루 러닝 목표 (km)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    val goalKilo: Int,
)
