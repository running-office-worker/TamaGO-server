package tamago.server.core.notification.domain.aggregate

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.enum.DeviceType
import tamago.server.core.notification.domain.vo.FcmTokenId
import java.time.LocalDateTime

class FcmToken(
    val id: FcmTokenId? = null,
    val userId: UserId,
    token: String? = null,
    val deviceType: DeviceType,
    lastUsedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    deletedAt: LocalDateTime? = null,
) {
    var token: String? = token
        private set

    var lastUsedAt: LocalDateTime? = lastUsedAt
        private set

    var deletedAt: LocalDateTime? = deletedAt
        private set

    fun updateToken(token: String) {
        this.token = token
        this.lastUsedAt = LocalDateTime.now()
    }

    fun updateLastUsedAt() {
        this.lastUsedAt = LocalDateTime.now()
    }

    fun delete() {
        this.deletedAt = LocalDateTime.now()
    }

    companion object {
        fun create(
            userId: UserId,
            token: String,
            deviceType: DeviceType,
        ): FcmToken =
            FcmToken(
                userId = userId,
                token = token,
                deviceType = deviceType,
                lastUsedAt = LocalDateTime.now(),
            )
    }
}
