package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity

object MonsterMapper {
    fun toEntity(monster: Monster, nextMonster: MonsterEntity? = null): MonsterEntity {
        return MonsterEntity(
            id = monster.id?.value,
            nextMonster = nextMonster,
        )
    }

    fun toDomain(entity: MonsterEntity?): Monster? {
        if (entity == null) return null

        return Monster(
            id = entity.id?.let { MonsterId(it) },
            nextMonsterId = entity.nextMonster?.id?.let { MonsterId(it) },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
