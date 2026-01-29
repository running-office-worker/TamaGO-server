package tamago.server.gateway.presentation.auth.v1.response

import io.swagger.v3.oas.annotations.media.Schema

data class SignupResponse(
    @field:Schema(description = "유저 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val userId: Long,

    @field:Schema(description = "이메일", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    val email: String,

    @field:Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    val accessToken: String,

    @field:Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    val refreshToken: String,
)
