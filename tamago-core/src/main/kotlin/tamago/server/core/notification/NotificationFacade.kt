package tamago.server.core.notification

import org.springframework.stereotype.Component
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.application.exception.FcmTokenNotFoundException
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
        val fcmToken =
            notificationQueryService
                .findFcmToken(command.userId, command.deviceId)
                ?.apply { updateToken(command.token) }
                ?: FcmToken.create(
                    userId = command.userId,
                    token = command.token,
                    deviceType = DeviceType.IOS,
                )
        notificationCommandService.save(fcmToken)
    }

    fun deleteFcmTokensByUserId(userId: UserId) {
        notificationCommandService.deleteFcmTokensByUserId(userId)
    }

    fun sendTestNotification(userId: UserId) {
        val tokens = notificationQueryService.getFcmTokensByUserId(userId)
        if (tokens.isEmpty()) throw FcmTokenNotFoundException()
        notificationCommandService.sendLetterArrivalNotification(
            tokens = tokens,
            title = "테스트 알림",
            body = "푸시 알림 테스트입니다.",
        )
    }
}
