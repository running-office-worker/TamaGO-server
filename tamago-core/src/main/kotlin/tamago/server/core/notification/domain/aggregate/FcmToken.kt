package tamago.server.core.notification.domain.aggregate

import tamago.server.core.notification.domain.enum.DeviceType
import tamago.server.core.notification.domain.vo.FcmTokenId
import tamago.server.core.common.vo.UserId
import java.time.LocalDateTime

class FcmToken(
    val id: FcmTokenId? = null,
    val userId: UserId,
    token: String? = null,
    val deviceType: DeviceType? = null,
    lastUsedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var token: String? = token
        private set

    var lastUsedAt: LocalDateTime? = lastUsedAt
        private set

    fun updateToken(token: String) {
        this.token = token
        this.lastUsedAt = LocalDateTime.now()
    }

    fun updateLastUsedAt() {
        this.lastUsedAt = LocalDateTime.now()
    }

    companion object {
        fun create(userId: UserId, token: String, deviceType: DeviceType): FcmToken {
            return FcmToken(
                userId = userId,
                token = token,
                deviceType = deviceType,
                lastUsedAt = LocalDateTime.now(),
            )
        }
    }
}
