package tamago.server.core.letter.application.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tamago.server.core.letter.application.service.UserLetterCommandService
import tamago.server.core.letter.application.service.UserLetterQueryService
import tamago.server.core.notification.NotificationCommandUseCase
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}
private const val LETTER_NOTIFICATION_TITLE = "타마고의 편지"
private const val LETTER_NOTIFICATION_CONTENT = "새로운 편지가 도착했어요."

@Component
class UserLetterSender(
    private val userLetterQueryService: UserLetterQueryService,
    private val userLetterCommandService: UserLetterCommandService,
    private val notificationCommandUseCase: NotificationCommandUseCase,
) {
    @Scheduled(fixedDelay = 60_000)
    fun notifyScheduledLetters() {
        val now = LocalDateTime.now()
        val scheduledLetters = userLetterQueryService.getScheduledLettersBeforeOrEqual(now)

        if (scheduledLetters.isEmpty()) return

        logger.info { "발송 대상 편지 ${scheduledLetters.size}건 발견" }

        // 편지 상태를 UNREAD로 전환
        userLetterCommandService.sendLetters(scheduledLetters)

        // 유저별로 알림 저장 (NotificationSender 스케줄러가 FCM 발송)
        val userIds = scheduledLetters.map { it.userId }.distinct()
        userIds.forEach { userId ->
            try {
                notificationCommandUseCase.createNotification(
                    userId = userId,
                    title = LETTER_NOTIFICATION_TITLE,
                    content = LETTER_NOTIFICATION_CONTENT,
                )
            } catch (e: Exception) {
                logger.error(e) { "편지 도착 알림 저장 중 오류 발생 - userId: $userId" }
            }
        }
    }
}
