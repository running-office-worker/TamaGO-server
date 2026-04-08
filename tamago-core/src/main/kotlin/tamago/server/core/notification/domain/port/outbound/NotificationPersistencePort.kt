package tamago.server.core.notification.domain.port.outbound

import tamago.server.core.notification.domain.aggregate.Notification

interface NotificationPersistencePort {
    fun save(notification: Notification): Notification

    fun findAllPending(): List<Notification>
}
