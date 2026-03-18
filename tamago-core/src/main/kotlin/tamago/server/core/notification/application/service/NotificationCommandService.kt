package tamago.server.core.notification.application.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.NotificationCommandUseCase
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.port.outbound.FcmSendPort
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class NotificationCommandService(
    private val fcmTokenPersistencePort: FcmTokenPersistencePort,
    private val fcmSendPort: FcmSendPort,
) : NotificationCommandUseCase {
    fun save(fcmToken: FcmToken) {
        fcmTokenPersistencePort.save(fcmToken)
    }

    override fun sendLetterArrivalNotification(
        tokens: List<String>,
        title: String,
        body: String,
    ) {
        fcmSendPort.send(
            tokens = tokens,
            title = title,
            body = body,
            destination = null,
        )
        logger.info { "편지 도착 알림 발송 완료 - tokenCount: ${tokens.size}" }
    }

    override fun deleteFcmTokensByUserId(userId: UserId) {
        val tokens = fcmTokenPersistencePort.findAllByUserId(userId)
        tokens.forEach { delete(it) }
    }

    fun delete(fcmToken: FcmToken) {
        fcmToken.delete()
        fcmTokenPersistencePort.save(fcmToken)
    }
}
