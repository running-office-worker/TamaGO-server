package tamago.server.core.notification.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_notifications")
class NotificationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long? = null,

    val title: String? = null,

    val content: String? = null,

    @Column(name = "is_read")
    var isRead: Boolean = false,

    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    val userId: Long,
) : BaseTimeEntity()
