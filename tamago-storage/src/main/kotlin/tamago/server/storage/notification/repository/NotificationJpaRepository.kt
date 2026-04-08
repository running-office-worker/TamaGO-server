package tamago.server.storage.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.notification.domain.enum.NotificationStatus
import tamago.server.storage.notification.entity.NotificationEntity

interface NotificationJpaRepository : JpaRepository<NotificationEntity, Long> {
    fun findAllByStatusAndDeletedAtIsNull(status: NotificationStatus): List<NotificationEntity>
}
