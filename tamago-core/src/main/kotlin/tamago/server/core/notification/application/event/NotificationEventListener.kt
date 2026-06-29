package tamago.server.core.notification.application.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.notification.application.service.NotificationCommandService

@Component
class NotificationEventListener(
    private val notificationCommandService: NotificationCommandService,
) {
    @EventListener
    fun onUserDeleted(event: UserDeletedEvent) {
        notificationCommandService.hardDeleteAllByUserId(event.userId)
    }
}
