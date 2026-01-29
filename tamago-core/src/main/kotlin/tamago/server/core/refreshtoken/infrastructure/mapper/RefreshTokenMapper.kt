package tamago.server.core.refreshtoken.infrastructure.mapper

import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.vo.RefreshTokenId
import tamago.server.core.refreshtoken.infrastructure.entity.RefreshTokenEntity
import tamago.server.core.common.vo.UserId

object RefreshTokenMapper {
    fun toEntity(refreshToken: RefreshToken): RefreshTokenEntity {
        return RefreshTokenEntity(
            id = refreshToken.id?.value,
            userId = refreshToken.userId.value,
            token = refreshToken.token,
        )
    }

    fun toDomain(entity: RefreshTokenEntity?): RefreshToken? {
        if (entity == null) return null

        return RefreshToken(
            id = entity.id?.let { RefreshTokenId(it) },
            userId = UserId(entity.userId),
            token = entity.token,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
