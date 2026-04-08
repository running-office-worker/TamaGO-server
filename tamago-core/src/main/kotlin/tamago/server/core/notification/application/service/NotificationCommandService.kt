package tamago.server.core.notification.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationCommandUseCase
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.aggregate.Notification
import tamago.server.core.notification.domain.port.outbound.FcmSendPort
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort
import tamago.server.core.notification.domain.port.outbound.NotificationPersistencePort

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class NotificationCommandService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
    private val notificationPersistencePort: NotificationPersistencePort,
    private val fcmSendPort: FcmSendPort,
) : NotificationCommandUseCase {
    fun save(fcmToken: FcmToken) {
        fcmTokenPersistencePort.save(fcmToken)
    }

    override fun createNotification(
        userId: UserId,
        title: String,
        content: String,
    ) {
        val notification = Notification.create(userId = userId, title = title, content = content)
        notificationPersistencePort.save(notification)
        logger.info { "알림 저장 완료 - userId: $userId" }
    }

    fun sendNotifications(notifications: List<Notification>) {
        notifications
            .groupBy { it.userId }
            .forEach { (userId, userNotifications) ->
                try {
                    val tokens =
                        fcmTokenPersistencePort
                            .findAllByUserId(userId)
                            .mapNotNull { it.token }
                    if (tokens.isEmpty()) {
                        logger.info { "FCM 토큰이 없어 알림 발송 생략 - userId: $userId" }
                        return@forEach
                    }
                    userNotifications.forEach { notification ->
                        fcmSendPort.send(
                            tokens = tokens,
                            title = notification.title,
                            body = notification.content ?: "",
                            destination = null,
                        )
                        notification.markAsSent()
                        notificationPersistencePort.save(notification)
                    }
                    logger.info { "알림 발송 완료 - userId: $userId, count: ${userNotifications.size}" }
                } catch (e: Exception) {
                    logger.error(e) { "알림 발송 중 오류 발생 - userId: $userId" }
                }
            }
    }

    fun deleteFcmTokensByUserId(userId: UserId) {
        fcmTokenPersistencePort.deleteAllByUserId(userId)
    }

    fun delete(fcmToken: FcmToken) {
        fcmTokenPersistencePort.delete(fcmToken)
    }
}
