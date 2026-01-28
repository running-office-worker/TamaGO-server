package tamago.server.core.notification.domain.aggregate

import tamago.server.core.notification.domain.vo.NotificationId
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class Notification(
    val id: NotificationId? = null,
    val title: String? = null,
    val content: String? = null,
    isRead: Boolean = false,
    val userId: UserId,
    val scheduledAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var isRead: Boolean = isRead
        private set

    fun markAsRead() {
        this.isRead = true
    }

    fun isScheduled(): Boolean = scheduledAt != null

    companion object {
        fun create(
            userId: UserId,
            title: String,
            content: String,
            scheduledAt: LocalDateTime? = null,
        ): Notification {
            return Notification(
                userId = userId,
                title = title,
                content = content,
                isRead = false,
                scheduledAt = scheduledAt,
            )
        }
    }
}
