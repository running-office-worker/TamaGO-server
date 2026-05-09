package tamago.server.core.letter.application.scheduler

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import tamago.server.core.letter.application.service.LetterQueryService
import tamago.server.core.letter.application.service.UserLetterCommandService
import tamago.server.core.letter.application.service.UserLetterQueryService
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.running.RunningQueryUseCase
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Component
class UserLetterScheduler(
    private val userLetterCommandService: UserLetterCommandService,
    private val userLetterQueryService: UserLetterQueryService,
    private val letterQueryService: LetterQueryService,
    private val runningQueryUseCase: RunningQueryUseCase,
) {
    @Scheduled(fixedDelay = 60_000) // 이전 실행 완료 후 1분마다 실행
    fun sendLetter() {
        val now = LocalDateTime.now()
        // 모든 유저의 SCHEDULED 상태가 아닌 마지막 편지 조회
        val letters = userLetterQueryService.getAllUsersLastSentLetter()

        // 마지막 발송 시각 24시간 뒤로 편지 예약
        letters.forEach { letter ->
            try {
                val hasScheduledLetterWithinOneDay =
                    userLetterQueryService
                        .findByUserId(letter.userId)
                        .any { userLetter ->
                            userLetter.deletedAt == null &&
                                userLetter.letterStatus == LetterStatus.SCHEDULED &&
                                userLetter.scheduledAt != null &&
                                !userLetter.scheduledAt!!.isBefore(now) &&
                                !userLetter.scheduledAt!!.isAfter(now.plusDays(1))
                        }
                if (hasScheduledLetterWithinOneDay) {
                    logger.info { "하루 이내 예약된 편지가 있어 다음 편지 예약 생략 - userId: ${letter.userId}" }
                    return@forEach
                }

                val streak = runningQueryUseCase.getRunningStreak(letter.userId)
                val tag = LetterTag.resolve(streak.isFirstRun, streak.streakDays, streak.inactiveDays)
                val selectedLetter = letterQueryService.getRandomTemplateByTag(tag)

                userLetterCommandService.scheduleNextLetter(
                    userId = letter.userId,
                    letterId = selectedLetter.id!!,
                    prevScheduledAt = letter.scheduledAt!!,
                )
            } catch (e: Exception) {
                logger.error(e) { "다음 편지 예약 중 오류 발생 - userId: ${letter.userId}, letterId: ${letter.letterId}" }
            }
        }
    }
}
