package tamago.server.core.fcmtoken.infrastructure.mapper

import tamago.server.core.fcmtoken.domain.aggregate.FcmToken
import tamago.server.core.fcmtoken.domain.vo.FcmTokenId
import tamago.server.core.fcmtoken.infrastructure.entity.FcmTokenEntity
import tamago.server.core.user.domain.vo.UserId

object FcmTokenMapper {
    fun toEntity(fcmToken: FcmToken): FcmTokenEntity {
        return FcmTokenEntity(
            id = fcmToken.id?.value,
            userId = fcmToken.userId.value,
            token = fcmToken.token,
            deviceType = fcmToken.deviceType,
            lastUsedAt = fcmToken.lastUsedAt,
        )
    }

    fun toDomain(entity: FcmTokenEntity?): FcmToken? {
        if (entity == null) return null

        return FcmToken(
            id = entity.id?.let { FcmTokenId(it) },
            userId = UserId(entity.userId),
            token = entity.token,
            deviceType = entity.deviceType,
            lastUsedAt = entity.lastUsedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
