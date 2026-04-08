package tamago.server.storage.notification.repository

import org.springframework.stereotype.Repository
import tamago.server.core.notification.domain.aggregate.Notification
import tamago.server.core.notification.domain.enum.NotificationStatus
import tamago.server.core.notification.domain.port.outbound.NotificationPersistencePort
import tamago.server.storage.notification.entity.NotificationEntity
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
            .findAll {
                select(entity(NotificationEntity::class))
                    .from(entity(NotificationEntity::class))
                    .where(
                        and(
                            path(NotificationEntity::status).equal(NotificationStatus.PENDING),
                            path(NotificationEntity::deletedAt).isNull(),
                        ),
                    )
            }.filterNotNull()
            .mapNotNull { NotificationMapper.toDomain(it) }
}
