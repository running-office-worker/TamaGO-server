package tamago.server.core.notification.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort

@Service
class NotificationQueryService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
) {
    fun getFcmTokensByUserId(userId: UserId): List<String> =
        fcmTokenPersistencePort
            .findAllByUserId(userId)
            .mapNotNull { it.token }

    fun findFcmTokenByUserId(userId: UserId): FcmToken? = fcmTokenPersistencePort.findByUserId(userId)
}
