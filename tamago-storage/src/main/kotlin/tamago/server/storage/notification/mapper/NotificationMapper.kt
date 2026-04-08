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
            status = notification.status,
            userId = notification.userId.value,
        )

    fun toDomain(entity: NotificationEntity?): Notification? {
        if (entity == null) return null

        return Notification(
            id = entity.id?.let { NotificationId(it) },
            title = entity.title,
            content = entity.content,
            status = entity.status,
            userId = UserId(entity.userId),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
