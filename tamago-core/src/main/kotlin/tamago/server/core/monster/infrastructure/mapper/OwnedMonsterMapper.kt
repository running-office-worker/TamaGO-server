package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.monster.infrastructure.entity.OwnedMonsterEntity

object OwnedMonsterMapper {
    fun toEntity(ownedMonster: OwnedMonster, monster: MonsterEntity): OwnedMonsterEntity {
        return OwnedMonsterEntity(
            id = ownedMonster.id?.value,
            monster = monster,
            userId = ownedMonster.userId.value,
            earnedXp = ownedMonster.earnedXp,
            status = ownedMonster.status,
        )
    }

    fun toDomain(entity: OwnedMonsterEntity?): OwnedMonster? {
        if (entity == null) return null

        return OwnedMonster(
            id = entity.id?.let { OwnedMonsterId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            userId = UserId(entity.userId),
            earnedXp = entity.earnedXp,
            status = entity.status,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
