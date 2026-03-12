package tamago.server.storage.notification.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.aggregate.Notification
import tamago.server.core.notification.domain.vo.NotificationId
import tamago.server.storage.notification.entity.NotificationEntity

object NotificationMapper {
    fun toEntity(notification: Notification): NotificationEntity =
        NotificationEntity(
            id = notification.id?.value,
            title = notification.title,
            content = notification.content,
            isRead = notification.isRead,
            userId = notification.userId.value,
            scheduledAt = notification.scheduledAt,
        )

    fun toDomain(entity: NotificationEntity?): Notification? {
        if (entity == null) return null

        return Notification(
            id = entity.id?.let { NotificationId(it) },
            title = entity.title,
            content = entity.content,
            isRead = entity.isRead,
            userId = UserId(entity.userId),
            scheduledAt = entity.scheduledAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
