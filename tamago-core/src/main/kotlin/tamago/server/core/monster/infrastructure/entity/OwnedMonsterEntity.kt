package tamago.server.core.monster.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_owned_monsters")
class OwnedMonsterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_monster_id")
    val id: Long? = null,

    @Column(name = "monster_id", nullable = false)
    val monsterId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long,
) : BaseTimeEntity()
