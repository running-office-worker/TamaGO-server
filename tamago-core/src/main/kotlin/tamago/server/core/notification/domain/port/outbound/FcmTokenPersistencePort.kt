package tamago.server.core.notification.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.aggregate.FcmToken

interface FcmTokenPersistencePort {
    fun findAllByUserId(userId: UserId): List<FcmToken>

    fun findByUserIdAndDeviceId(
        userId: UserId,
        deviceId: String?,
    ): FcmToken?

    fun save(fcmToken: FcmToken): FcmToken

    fun delete(fcmToken: FcmToken)

    fun deleteAllByUserId(userId: UserId)
}
