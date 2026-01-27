package tamago.server.gateway.presentation.auth.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ReissueRequest(
    @field:Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotBlank(message = "리프레시 토큰은 필수입니다.")
    val refreshToken: String,
)
