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

    override fun sendLetterArrivalNotification(userId: UserId) {
        val tokens =
            fcmTokenPersistencePort
                .findAllByUserId(userId)
                .mapNotNull { it.token }

        if (tokens.isEmpty()) {
            logger.info { "FCM 토큰이 없어 알림 발송 생략 - userId: $userId" }
            return
        }

        try {
            fcmSendPort.send(
                tokens = tokens,
                title = "Tamago",
                body = "타마고가 보낸 편지가 도착했어요!",
                destination = null,
            )
            logger.info { "편지 도착 알림 발송 완료 - userId: $userId, tokenCount: ${tokens.size}" }
        } catch (e: Exception) {
            logger.error(e) { "편지 도착 알림 발송 실패 - userId: $userId" }
        }
    }
}
