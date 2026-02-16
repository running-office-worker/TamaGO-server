package tamago.server.core.notification.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import tamago.server.core.notification.domain.enum.DeviceType
import java.time.LocalDateTime

@Entity
@Table(name = "t_fcm_token")
class FcmTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_token_id")
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    var token: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    val deviceType: DeviceType,

    var lastUsedAt: LocalDateTime? = null,
) : BaseTimeEntity()
