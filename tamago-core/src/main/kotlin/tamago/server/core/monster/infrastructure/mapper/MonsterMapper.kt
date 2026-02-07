package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity

object MonsterMapper {
    fun toEntity(monster: Monster, previousMonster: MonsterEntity? = null, nextMonster: MonsterEntity? = null): MonsterEntity {
        return MonsterEntity(
            id = monster.id?.value,
            previousMonster = previousMonster,
            nextMonster = nextMonster,
            nickname = monster.nickname,
            evolutionXp = monster.evolutionXp,
        )
    }

    fun toDomain(entity: MonsterEntity?): Monster? {
        if (entity == null) return null

        return Monster(
            id = entity.id?.let { MonsterId(it) },
            previousMonsterId = entity.previousMonster?.id?.let { MonsterId(it) },
            nextMonsterId = entity.nextMonster?.id?.let { MonsterId(it) },
            nickname = entity.nickname,
            evolutionXp = entity.evolutionXp,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
