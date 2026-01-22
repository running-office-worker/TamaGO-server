package tamago.server.core.user.infrastructure.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "t_users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var nickname: String? = null,

    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val oauths: MutableList<UserOAuthEntity> = mutableListOf(),

    var lastLoginAt: LocalDateTime? = null,
) : BaseTimeEntity()