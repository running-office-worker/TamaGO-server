package tamago.server.core.monster.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus

@Entity
@Table(name = "t_owned_monsters")
class OwnedMonsterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owned_monster_id")
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "monster_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val monster: MonsterEntity,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "having_xp")
    val havingXp: Int? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: OwnedMonsterStatus? = null,
) : BaseTimeEntity()
