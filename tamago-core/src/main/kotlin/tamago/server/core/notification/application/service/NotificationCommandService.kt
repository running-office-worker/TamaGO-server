package tamago.server.core.notification.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort

@Service
@Transactional
class NotificationCommandService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
) {
    fun save(fcmToken: FcmToken) {
        fcmTokenPersistencePort.save(fcmToken)
    }
}
