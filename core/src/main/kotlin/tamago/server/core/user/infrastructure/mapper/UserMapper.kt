package tamago.server.core.user.infrastructure.mapper

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.infrastructure.entity.UserEntity

object UserMapper {
    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id?.value,
            lastLoginAt = user.lastLoginAt,
        )
    }

    fun toDomain(entity: UserEntity?): User? {
        if (entity == null) return null

        return User(
            id = UserId(entity.id ?: 0L),
            oauths = UserOAuthMapper.toDomain(entity.oauths),
            lastLoginAt = entity.lastLoginAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            withdrawAt = entity.withdrawAt,
        )
    }
}