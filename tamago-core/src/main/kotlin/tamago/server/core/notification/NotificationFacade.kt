package tamago.server.core.notification

import org.springframework.stereotype.Component
import tamago.server.core.notification.application.service.NotificationCommandService
import tamago.server.core.notification.application.service.NotificationQueryService
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.enum.DeviceType
import tamago.server.core.notification.domain.port.inbound.command.RegisterFcmTokenCommandDto

@Component
class NotificationFacade(
    private val notificationCommandService: NotificationCommandService,
    private val notificationQueryService: NotificationQueryService,
) {
    fun registerFcmToken(command: RegisterFcmTokenCommandDto) {
        val fcmToken = notificationQueryService.findFcmTokenByUserId(command.userId)
            ?.apply { updateToken(command.token) }
            ?: FcmToken.create(
                userId = command.userId,
                token = command.token,
                deviceType = DeviceType.IOS
            )
        notificationCommandService.save(fcmToken)
    }
}
