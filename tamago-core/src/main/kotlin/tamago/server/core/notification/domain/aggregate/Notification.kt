package tamago.server.core.notification.domain.aggregate

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.enum.NotificationStatus
import tamago.server.core.notification.domain.vo.NotificationId
import java.time.LocalDateTime

class Notification(
    val id: NotificationId? = null,
    val title: String? = null,
    val content: String? = null,
    status: NotificationStatus = NotificationStatus.PENDING,
    val userId: UserId,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var status: NotificationStatus = status
        private set

    fun markAsSent() {
        this.status = NotificationStatus.SENT
    }

    fun markAsRead() {
        this.status = NotificationStatus.READ
    }

    companion object {
        fun create(
            userId: UserId,
            title: String,
            content: String,
        ): Notification =
            Notification(
                userId = userId,
                title = title,
                content = content,
                status = NotificationStatus.PENDING,
            )
    }
}
