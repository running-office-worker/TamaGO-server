package tamago.server.core.fcmtoken.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import tamago.server.core.fcmtoken.domain.enum.DeviceType
import java.time.LocalDateTime

@Entity
@Table(name = "t_fcm_token")
class FcmTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_id")
    val id: Long? = null,

    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    val userId: Long,

    var token: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    val deviceType: DeviceType? = null,

    var lastUsedAt: LocalDateTime? = null,
) : BaseTimeEntity()
