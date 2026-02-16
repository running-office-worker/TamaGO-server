package tamago.server.gateway.presentation.notification.v1.controller

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationFacade
import tamago.server.core.notification.domain.port.inbound.command.RegisterFcmTokenCommandDto
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.notification.v1.api.NotificationApi
import tamago.server.gateway.presentation.notification.v1.request.FcmTokenRequest

@RestController
class NotificationController(
    private val notificationFacade: NotificationFacade,
) : NotificationApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/fcm/token")
    override fun registerFcmToken(
        @CurrentUser user: User,
        @Valid @RequestBody request: FcmTokenRequest,
    ): CustomResponse<Void> {
        val command = RegisterFcmTokenCommandDto(
            userId = UserId(user.id!!.value),
            token = request.token,
        )
        notificationFacade.registerFcmToken(command)
        return CustomResponse.ok()
    }
}
