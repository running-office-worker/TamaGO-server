package tamago.server.core.notification.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationQueryUseCase
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort

@Service
class NotificationQueryService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
) : NotificationQueryUseCase {
    override fun getFcmTokensByUserId(userId: UserId): List<String> =
        fcmTokenPersistencePort
            .findAllByUserId(userId)
            .mapNotNull { it.token }

    fun findFcmToken(
        userId: UserId,
        deviceId: String?,
    ): FcmToken? = fcmTokenPersistencePort.findByUserIdAndDeviceId(userId, deviceId)
}
