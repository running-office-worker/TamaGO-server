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

        user.auths.forEach { auth ->
            val authEntity = UserAuthMapper.toEntity(auth)
            authEntity.user = userEntity
            userEntity.auths.add(authEntity)
        }

        return userEntity
    }

    fun toDomain(entity: UserEntity?): User? {
        if (entity == null) return null

        return User(
            id = UserId(entity.id ?: 0L),
            nickname = entity.nickname,
            role = entity.role,
            auths = UserAuthMapper.toDomain(entity.auths),
            lastLoginAt = entity.lastLoginAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
