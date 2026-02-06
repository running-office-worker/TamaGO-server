package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.MonsterRuleType
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.MonsterEvolutionPolicyId
import java.time.LocalDateTime

class MonsterEvolutionPolicy(
    val id: MonsterEvolutionPolicyId? = null,
    val monsterId: MonsterId,
    val ruleType: MonsterRuleType? = null,
    val ruleValue: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            monsterId: MonsterId,
            ruleType: MonsterRuleType? = null,
            ruleValue: Int? = null,
        ): MonsterEvolutionPolicy {
            return MonsterEvolutionPolicy(
                monsterId = monsterId,
                ruleType = ruleType,
                ruleValue = ruleValue,
            )
        }
    }
}
