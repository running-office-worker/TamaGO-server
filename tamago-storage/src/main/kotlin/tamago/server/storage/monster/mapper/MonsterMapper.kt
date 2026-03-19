package tamago.server.storage.monster.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.storage.monster.entity.MonsterEntity

object MonsterMapper {
    fun toEntity(
        monster: Monster,
        previousMonster: MonsterEntity? = null,
        nextMonster: MonsterEntity? = null,
    ): MonsterEntity =
        MonsterEntity(
            id = monster.id?.value,
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
            previousMonsterId = entity.previousMonster?.id?.let { MonsterId(it) },
            nextMonsterId = entity.nextMonster?.id?.let { MonsterId(it) },
            nickname = entity.nickname,
            monsterType = entity.monsterType,
            evolutionXp = entity.evolutionXp,
            evolutionPolicy = MonsterEvolutionPolicyMapper.toDomain(entity.evolutionPolicy),
            unlockPolicies = entity.unlockPolicies.mapNotNull { MonsterUnlockPolicyMapper.toDomain(it) },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
