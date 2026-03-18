package tamago.server.storage.monster.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.storage.monster.entity.MonsterEntity
import tamago.server.storage.monster.entity.OwnedMonsterEntity

object OwnedMonsterMapper {
    fun toEntity(
        ownedMonster: OwnedMonster,
        monster: MonsterEntity,
    ): OwnedMonsterEntity =
        OwnedMonsterEntity(
            id = ownedMonster.id?.value,
            monster = monster,
            userId = ownedMonster.userId.value,
            havingXp = ownedMonster.havingXp,
            status = ownedMonster.status,
            deletedAt = ownedMonster.deletedAt,
        )

    fun toDomain(entity: OwnedMonsterEntity?): OwnedMonster? {
        if (entity == null) return null

        return OwnedMonster(
            id = entity.id?.let { OwnedMonsterId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            userId = UserId(entity.userId),
            havingXp = entity.havingXp,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
