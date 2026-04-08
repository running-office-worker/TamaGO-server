package tamago.server.storage.notification.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.notification.domain.enum.NotificationStatus
import tamago.server.storage.support.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "t_notifications")
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long? = null,
    val title: String? = null,
    val content: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: NotificationStatus = NotificationStatus.PENDING,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "scheduled_at")
    val scheduledAt: LocalDateTime? = null,
) : BaseTimeEntity()
