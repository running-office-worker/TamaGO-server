package tamago.server.gateway.presentation.auth.v1.response

import io.swagger.v3.oas.annotations.media.Schema

data class LoginResponse(
    @field:Schema(description = "유저 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val userId: Long,

    @field:Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    val accessToken: String,

    @field:Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    val refreshToken: String,

    @field:Schema(description = "신규 유저 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    val newUser: Boolean,
)
