package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.MonsterRuleType
import tamago.server.core.monster.domain.vo.MonsterEvolutionPolicyId
import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

class MonsterEvolutionPolicy(
    val id: MonsterEvolutionPolicyId? = null,
    val monsterId: MonsterId,
    val ruleType: MonsterRuleType? = null,
    val multiplier: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun calculateXp(distance: Double): Int =
        when (ruleType) {
            MonsterRuleType.MONSTER_KILOMETER -> (distance * (multiplier ?: 0)).toInt()
            MonsterRuleType.TOTAL_KILOMETER, MonsterRuleType.TOTAL_DURATION -> 0
            null -> 0
        }

    companion object {
        fun create(
            monsterId: MonsterId,
            ruleType: MonsterRuleType? = null,
            multiplier: Int? = null,
        ): MonsterEvolutionPolicy =
            MonsterEvolutionPolicy(
                monsterId = monsterId,
                ruleType = ruleType,
                multiplier = multiplier,
            )
    }
}
