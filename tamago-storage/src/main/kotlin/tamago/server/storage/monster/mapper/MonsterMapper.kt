package tamago.server.storage.monster.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.vo.MonsterGroupId
import tamago.server.storage.monster.entity.MonsterEntity
import tamago.server.storage.monster.entity.MonsterGroupEntity

object MonsterMapper {
    fun toEntity(
        monster: Monster,
        monsterGroup: MonsterGroupEntity? = null,
        previousMonster: MonsterEntity? = null,
        nextMonster: MonsterEntity? = null,
    ): MonsterEntity =
        MonsterEntity(
            id = monster.id?.value,
            monsterGroup = monsterGroup,
            evolutionStage = monster.evolutionStage,
            previousMonster = previousMonster,
            nextMonster = nextMonster,
            nickname = monster.nickname,
            monsterType = monster.monsterType,
            evolutionXp = monster.evolutionXp,
        )

    fun toDomain(entity: MonsterEntity?): Monster? {
        if (entity == null) return null

        return Monster(
            id = entity.id?.let { MonsterId(it) },
            monsterGroupId = entity.monsterGroup?.id?.let { MonsterGroupId(it) },
            monsterGroupCode = entity.monsterGroup?.code,
            monsterGroupName = entity.monsterGroup?.name,
            previousMonsterId = entity.previousMonster?.id?.let { MonsterId(it) },
            nextMonsterId = entity.nextMonster?.id?.let { MonsterId(it) },
            nickname = entity.nickname,
            monsterType = entity.monsterType,
            evolutionStage = entity.evolutionStage,
            evolutionXp = entity.evolutionXp,
            evolutionPolicy = MonsterEvolutionPolicyMapper.toDomain(entity.evolutionPolicy),
            unlockPolicies = entity.unlockPolicies.mapNotNull { MonsterUnlockPolicyMapper.toDomain(it) },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
