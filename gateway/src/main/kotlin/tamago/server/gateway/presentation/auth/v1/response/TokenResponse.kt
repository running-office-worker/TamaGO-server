package tamago.server.gateway.presentation.auth.v1.response

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponse(
    @field:Schema(description = "액세스 토큰", example = "accessToken")
    val accessToken: String,
    @field:Schema(description = "리프레시 토큰", example = "refreshToken")
    val refreshToken: String,
)
