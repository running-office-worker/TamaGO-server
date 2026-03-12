package tamago.server.storage.user.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import tamago.server.core.user.domain.enum.UserRole
import tamago.server.storage.common.entity.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "t_users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var nickname: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER,
    @OneToMany(
        mappedBy = "user",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
    )
    val auths: MutableList<UserAuthEntity> = mutableListOf(),
    var goalKilo: Int? = null,
    var totalKilo: Double? = null,
    @Column(nullable = false)
    var weight: Double = 70.0,
    var lastLoginAt: LocalDateTime? = null,
) : BaseTimeEntity()
