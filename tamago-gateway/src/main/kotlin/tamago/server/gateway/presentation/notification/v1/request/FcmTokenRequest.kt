package tamago.server.gateway.presentation.notification.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class FcmTokenRequest(
    @field:Schema(description = "FCM 토큰", example = "fcm-token-value", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotBlank(message = "FCM 토큰은 필수입니다.")
    val token: String,
)
