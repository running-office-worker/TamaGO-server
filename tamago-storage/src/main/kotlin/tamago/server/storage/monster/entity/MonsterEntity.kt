package tamago.server.storage.monster.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import tamago.server.core.monster.domain.enum.MonsterType
import tamago.server.storage.support.BaseTimeEntity

@Entity
@Table(name = "t_monsters")
class MonsterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monster_id")
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "previous_monster_id")
    val previousMonster: MonsterEntity? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "next_monster_id")
    val nextMonster: MonsterEntity? = null,
    val nickname: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "monster_type")
    val monsterType: MonsterType? = null,
    @Column(name = "evolution_xp")
    val evolutionXp: Int? = null,
    @OneToMany(mappedBy = "monster", cascade = [CascadeType.ALL], orphanRemoval = true)
    val unlockPolicies: MutableList<MonsterUnlockPolicyEntity> = mutableListOf(),
    @OneToOne(mappedBy = "monster", cascade = [CascadeType.ALL], orphanRemoval = true)
    val evolutionPolicy: MonsterEvolutionPolicyEntity? = null,
) : BaseTimeEntity()
