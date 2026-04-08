package tamago.server.gateway.presentation.notification.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationFacade
import tamago.server.core.notification.domain.port.inbound.command.RegisterFcmTokenCommandDto
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.notification.v1.request.FcmTokenRequest
import tamago.server.gateway.presentation.notification.v1.request.TestNotificationRequest

@Tag(name = "Notification API", description = "푸시 알림 관련 API")
@RestController
class NotificationController(
    private val notificationFacade: NotificationFacade,
) {
    @Operation(summary = "FCM 토큰 등록/갱신", description = "FCM 푸시 알림 토큰을 등록하거나 갱신합니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/fcm/token")
    fun registerFcmToken(
        @CurrentUser user: User,
        @RequestHeader("X-DEVICE-ID") deviceId: String?,
        @Valid @RequestBody request: FcmTokenRequest,
    ): CustomResponse<Void> {
        val command =
            RegisterFcmTokenCommandDto(
                userId = UserId(user.id!!.value),
                deviceId = deviceId,
                token = request.token,
            )
        notificationFacade.registerFcmToken(command)
        return CustomResponse.ok()
    }

    @Operation(summary = "\uD83E\uDDEA 푸시 알림 테스트", description = "특정 유저에게 테스트 푸시 알림을 발송합니다.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/fcm/test")
    fun sendTestNotification(
        @Valid @RequestBody request: TestNotificationRequest,
    ): CustomResponse<Void> {
        notificationFacade.sendTestNotification(UserId(request.userId))
        return CustomResponse.ok()
    }
}
