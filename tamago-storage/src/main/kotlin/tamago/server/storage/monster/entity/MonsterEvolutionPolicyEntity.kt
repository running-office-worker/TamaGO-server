package tamago.server.storage.monster.entity

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
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import tamago.server.core.monster.domain.enum.MonsterRuleType
import tamago.server.storage.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_monster_evolution_policy")
class MonsterEvolutionPolicyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_evolution_policy_id")
    val id: Long? = null,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "monster_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val monster: MonsterEntity,
    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type")
    val ruleType: MonsterRuleType? = null,
    @Column(name = "multiplier")
    val multiplier: Int? = null,
) : BaseTimeEntity()
