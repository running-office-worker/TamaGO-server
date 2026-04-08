package tamago.server.gateway.presentation.notification.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

data class TestNotificationRequest(
    @field:Schema(description = "알림을 보낼 유저 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotNull(message = "유저 ID는 필수입니다.")
    val userId: Long,
)
