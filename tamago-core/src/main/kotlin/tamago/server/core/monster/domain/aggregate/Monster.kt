package tamago.server.core.monster.domain.aggregate

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.enum.MonsterType
import tamago.server.core.monster.domain.vo.MonsterGroupId
import java.time.LocalDateTime

class Monster(
    val id: MonsterId? = null,
    val monsterGroupId: MonsterGroupId? = null,
    val monsterGroupCode: String? = null,
    val monsterGroupName: String? = null,
    val previousMonsterId: MonsterId? = null,
    val nextMonsterId: MonsterId? = null,
    val nickname: String? = null,
    val monsterType: MonsterType? = null,
    val evolutionStage: Int? = null,
    val evolutionXp: Int? = null,
    val evolutionPolicy: MonsterEvolutionPolicy? = null,
    val unlockPolicies: List<MonsterUnlockPolicy> = emptyList(),
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            monsterGroupId: MonsterGroupId? = null,
            monsterGroupCode: String? = null,
            monsterGroupName: String? = null,
            previousMonsterId: MonsterId? = null,
            nextMonsterId: MonsterId? = null,
            nickname: String? = null,
            monsterType: MonsterType? = null,
            evolutionStage: Int? = null,
            evolutionXp: Int? = null,
        ): Monster =
            Monster(
                monsterGroupId = monsterGroupId,
                monsterGroupCode = monsterGroupCode,
                monsterGroupName = monsterGroupName,
                previousMonsterId = previousMonsterId,
                nextMonsterId = nextMonsterId,
                nickname = nickname,
                monsterType = monsterType,
                evolutionStage = evolutionStage,
                evolutionXp = evolutionXp,
            )
    }
}
