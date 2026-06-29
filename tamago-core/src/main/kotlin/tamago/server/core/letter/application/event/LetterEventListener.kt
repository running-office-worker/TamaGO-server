package tamago.server.core.letter.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.RunningCompletedEvent
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.common.event.UserRestoredEvent
import tamago.server.core.letter.application.service.LetterQueryService
import tamago.server.core.letter.application.service.UserLetterCommandService
import tamago.server.core.letter.application.service.UserLetterQueryService
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.enum.LetterTag

private val logger = KotlinLogging.logger {}

@Component
class LetterEventListener(
    private val letterQueryService: LetterQueryService,
    private val userLetterQueryService: UserLetterQueryService,
    private val userLetterCommandService: UserLetterCommandService,
) {
    @EventListener
    fun onUserDeleted(event: UserDeletedEvent) {
        userLetterCommandService.hardDeleteAllByUserId(event.userId)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserRestored(event: UserRestoredEvent) {
        try {
            val userLetters = userLetterQueryService.findAllDeletedByUserId(event.userId)
            userLetters.forEach { userLetterCommandService.restore(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 복구 후 편지 데이터 복구 오류 - userId: ${event.userId}" }
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onRunningCompleted(event: RunningCompletedEvent) {
        try {
            // 이미 스케줄된 편지가 있는 경우 삭제
            val scheduledLetter =
                userLetterQueryService.findByUserIdAndLetterStatus(event.userId, LetterStatus.SCHEDULED)
            if (scheduledLetter != null) {
                userLetterCommandService.delete(scheduledLetter)
            }

            // 러닝 상태에 맞는 태그 결정하고 다음 편지 세팅
            val tag = LetterTag.resolve(event.isFirstRun, event.streakDays, event.inactiveDays)
            val selectedLetter = letterQueryService.getRandomTemplateByTag(tag)

            // 러닝 시작 시간 기준 23시간 후로 편지 발송 시간 설정
            val createdLetter =
                UserLetter.new(
                    userId = event.userId,
                    letterId = selectedLetter.id!!,
                    startedAt = event.startedAt,
                )
            userLetterCommandService.create(createdLetter)
        } catch (e: Exception) {
            logger.error(e) { "러닝 완료 후 편지 발송 시간 조정 오류 - userId: ${event.userId}" }
        }
    }
}
