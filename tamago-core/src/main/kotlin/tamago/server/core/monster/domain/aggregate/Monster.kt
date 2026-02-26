package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

class Monster(
    val id: MonsterId? = null,
    val previousMonsterId: MonsterId? = null,
    val nextMonsterId: MonsterId? = null,
    val nickname: String? = null,
    val evolutionXp: Int? = null,
    val evolutionPolicy: MonsterEvolutionPolicy? = null,
    val unlockPolicies: List<MonsterUnlockPolicy> = emptyList(),
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            previousMonsterId: MonsterId? = null,
            nextMonsterId: MonsterId? = null,
            nickname: String? = null,
            evolutionXp: Int? = null,
        ): Monster =
            Monster(
                previousMonsterId = previousMonsterId,
                nextMonsterId = nextMonsterId,
                nickname = nickname,
                evolutionXp = evolutionXp,
            )
    }
}
