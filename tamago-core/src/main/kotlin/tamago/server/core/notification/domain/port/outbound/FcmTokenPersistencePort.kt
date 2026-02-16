package tamago.server.core.notification.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.aggregate.FcmToken

interface FcmTokenPersistencePort {
    fun findAllByUserId(userId: UserId): List<FcmToken>
    fun findByUserId(userId: UserId): FcmToken?
    fun save(fcmToken: FcmToken): FcmToken
}
