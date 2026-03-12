package tamago.server.storage.refreshtoken.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.vo.RefreshTokenId
import tamago.server.storage.refreshtoken.entity.RefreshTokenEntity

object RefreshTokenMapper {
    fun toEntity(refreshToken: RefreshToken): RefreshTokenEntity =
        RefreshTokenEntity(
            id = refreshToken.id?.value,
            userId = refreshToken.userId.value,
            token = refreshToken.token,
        )

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
