package tamago.server.core.notification.application.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tamago.server.core.notification.application.service.NotificationCommandService
import tamago.server.core.notification.domain.port.outbound.NotificationPersistencePort

private val logger = KotlinLogging.logger {}

@Component
class NotificationSender(
    private val notificationPersistencePort: NotificationPersistencePort,
    private val notificationCommandService: NotificationCommandService,
) {
    @Scheduled(fixedDelay = 60_000)
    fun sendPendingNotifications() {
        val pendingNotifications = notificationPersistencePort.findAllPending()

        if (pendingNotifications.isEmpty()) return

        logger.info { "발송 대기 알림 ${pendingNotifications.size}건 발견" }

        notificationCommandService.sendNotifications(pendingNotifications)
    }
}
