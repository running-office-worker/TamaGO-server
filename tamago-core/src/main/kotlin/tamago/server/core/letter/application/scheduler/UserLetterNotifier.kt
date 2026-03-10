package tamago.server.core.letter.application.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tamago.server.core.letter.application.service.UserLetterCommandService
import tamago.server.core.letter.application.service.UserLetterQueryService
import tamago.server.core.notification.NotificationCommandUseCase
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Component
class UserLetterNotifier(
    private val userLetterQueryService: UserLetterQueryService,
    private val userLetterCommandService: UserLetterCommandService,
    private val notificationCommandUseCase: NotificationCommandUseCase,
) {
    @Scheduled(fixedDelay = 60_000)
    fun notifyScheduledLetters() {
        val now = LocalDateTime.now()
        val scheduledLetters = userLetterQueryService.getScheduledLettersBefore(now)

        if (scheduledLetters.isEmpty()) return

        logger.info { "발송 대상 편지 ${scheduledLetters.size}건 발견" }

        // 편지 상태를 UNREAD로 전환
        userLetterCommandService.sendLetters(scheduledLetters)

        // 유저별로 FCM 알림 발송 (알림 실패가 편지 발송을 막지 않음)
        val userIds = scheduledLetters.map { it.userId }.distinct()
        userIds.forEach { userId ->
            try {
                notificationCommandUseCase.sendLetterArrivalNotification(userId)
            } catch (e: Exception) {
                logger.error(e) { "편지 도착 알림 발송 중 오류 발생 - userId: $userId" }
            }
        }
    }
}
