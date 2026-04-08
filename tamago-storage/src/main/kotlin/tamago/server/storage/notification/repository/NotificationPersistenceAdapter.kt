package tamago.server.storage.notification.repository

import org.springframework.stereotype.Repository
import tamago.server.core.notification.domain.aggregate.Notification
import tamago.server.core.notification.domain.enum.NotificationStatus
import tamago.server.core.notification.domain.port.outbound.NotificationPersistencePort
import tamago.server.storage.notification.mapper.NotificationMapper

@Repository
class NotificationPersistenceAdapter(
    private val notificationJpaRepository: NotificationJpaRepository,
) : NotificationPersistencePort {
    override fun save(notification: Notification): Notification {
        val entity = NotificationMapper.toEntity(notification)
        val saved = notificationJpaRepository.save(entity)
        return NotificationMapper.toDomain(saved)!!
    }

    override fun findAllPending(): List<Notification> =
        notificationJpaRepository
            .findAllByStatusAndDeletedAtIsNull(NotificationStatus.PENDING)
            .mapNotNull { NotificationMapper.toDomain(it) }
}
