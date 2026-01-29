package tamago.server.core.usermonster.infrastructure.mapper

import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.infrastructure.entity.UserEntity
import tamago.server.core.usermonster.domain.aggregate.UserMonster
import tamago.server.core.usermonster.domain.vo.UserMonsterId
import tamago.server.core.usermonster.infrastructure.entity.UserMonsterEntity

object UserMonsterMapper {
    fun toEntity(userMonster: UserMonster, monster: MonsterEntity, user: UserEntity): UserMonsterEntity {
        return UserMonsterEntity(
            id = userMonster.id?.value,
            monster = monster,
            user = user,
        )
    }

    fun toDomain(entity: UserMonsterEntity?): UserMonster? {
        if (entity == null) return null

        return UserMonster(
            id = entity.id?.let { UserMonsterId(it) },
            monsterId = MonsterId(checkNotNull(entity.monster.id)),
            userId = UserId(checkNotNull(entity.user.id)),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
