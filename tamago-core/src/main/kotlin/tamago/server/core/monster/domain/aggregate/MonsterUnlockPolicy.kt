package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.MonsterRuleType
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.MonsterUnlockPolicyId
import java.time.LocalDateTime

class MonsterUnlockPolicy(
    val id: MonsterUnlockPolicyId? = null,
    val monsterId: MonsterId,
    val ruleType: MonsterRuleType? = null,
    val ruleValue: Int? = null,
    val description: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun isSatisfiedBy(totalDistance: Double): Boolean =
        when (ruleType) {
            MonsterRuleType.KILOMETER -> (ruleValue ?: Int.MAX_VALUE) <= totalDistance
            null -> false
        }
}
