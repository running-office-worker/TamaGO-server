package tamago.server.storage.monster.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.storage.support.BaseTimeEntity

@Entity
@Table(name = "t_monster_groups")
class MonsterGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_group_id")
    val id: Long? = null,
    @Column(name = "code")
    val code: String,
    @Column(name = "name")
    val name: String? = null,
) : BaseTimeEntity()
