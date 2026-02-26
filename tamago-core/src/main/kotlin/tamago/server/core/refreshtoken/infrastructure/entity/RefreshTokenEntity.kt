package tamago.server.core.refreshtoken.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_refresh_token")
class RefreshTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    val id: Long? = null,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    var token: String? = null,
) : BaseTimeEntity()
