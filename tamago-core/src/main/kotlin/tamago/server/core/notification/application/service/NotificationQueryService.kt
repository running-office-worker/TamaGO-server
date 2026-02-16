package tamago.server.core.notification.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationQueryUseCase
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort

@Service
class NotificationQueryService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
) : NotificationQueryUseCase {

    override fun getFcmTokensByUserId(userId: UserId): List<String> {
        return fcmTokenPersistencePort.findAllByUserId(userId)
            .mapNotNull { it.token }
    }
}
