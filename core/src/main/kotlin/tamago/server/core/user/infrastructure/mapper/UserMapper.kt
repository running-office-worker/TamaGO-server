package tamago.server.core.user.infrastructure.mapper

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.infrastructure.entity.UserEntity

object UserMapper {
    fun toEntity(user: User): UserEntity {
        val userEntity = UserEntity(
            id = user.id?.value,
            nickname = user.nickname,
            role = user.role,
            lastLoginAt = user.lastLoginAt,
        )

        user.oauths.forEach { oauth ->
            val oauthEntity = UserOAuthMapper.toEntity(oauth)
            oauthEntity.user = userEntity
            userEntity.oauths.add(oauthEntity)
        }

        return userEntity
    }

    fun toDomain(entity: UserEntity?): User? {
        if (entity == null) return null

        return User(
            id = UserId(entity.id ?: 0L),
            nickname = entity.nickname,
            role = entity.role,
            oauths = UserOAuthMapper.toDomain(entity.oauths),
            lastLoginAt = entity.lastLoginAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}