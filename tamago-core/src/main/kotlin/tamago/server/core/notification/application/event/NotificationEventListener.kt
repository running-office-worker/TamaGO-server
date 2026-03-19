package tamago.server.core.notification.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.notification.application.service.NotificationCommandService
import tamago.server.core.notification.application.service.NotificationQueryService

private val logger = KotlinLogging.logger {}

@Component
class NotificationEventListener(
    private val notificationQueryService: NotificationQueryService,
    private val notificationCommandService: NotificationCommandService,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserDeleted(event: UserDeletedEvent) {
        try {
            val fcmTokens = notificationQueryService.findFcmTokensByUserId(event.userId)
            fcmTokens.forEach { notificationCommandService.delete(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 탈퇴 후 FCM 토큰 삭제 오류 - userId: ${event.userId}" }
        }
    }
}
