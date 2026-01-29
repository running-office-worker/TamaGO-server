package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.monster.infrastructure.entity.OwnedMonsterEntity
import tamago.server.core.user.domain.vo.UserId

object OwnedMonsterMapper {
    fun toEntity(ownedMonster: OwnedMonster): OwnedMonsterEntity {
        return OwnedMonsterEntity(
            id = ownedMonster.id?.value,
            monsterId = ownedMonster.monsterId.value,
            userId = ownedMonster.userId.value,
        )
    }

    fun toDomain(entity: OwnedMonsterEntity?): OwnedMonster? {
        if (entity == null) return null

        return OwnedMonster(
            id = entity.id?.let { OwnedMonsterId(it) },
            monsterId = MonsterId(entity.monsterId),
            userId = UserId(entity.userId),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
