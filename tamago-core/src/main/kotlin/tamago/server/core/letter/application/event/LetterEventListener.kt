package tamago.server.core.letter.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.running.domain.event.RunningCompletedEvent
import tamago.server.core.letter.application.service.LetterQueryService
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.port.outbound.UserLetterPersistencePort
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Component
class LetterEventListener(
    private val letterQueryService: LetterQueryService,
    private val userLetterPersistencePort: UserLetterPersistencePort,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onRunningCompleted(event: RunningCompletedEvent) {
        try {
            // 이미 스케줄된 편지가 있는 경우, 편지 발송 시간을 재조정
            userLetterPersistencePort.findByUserIdAndLetterStatus(event.userId, LetterStatus.SCHEDULED)?.let {
                it.reschedule(LocalDateTime.now())
                userLetterPersistencePort.save(it)
                return
            }

            // 스케줄된 편지가 없는 경우, 새로운 편지 생성
            val selectedLetter = letterQueryService.getRandomTemplate()

            // 러닝 시작 시간 기준 23시간 후로 편지 발송 시간 설정
            val createdLetter = UserLetter.new(
                userId = event.userId,
                letterId = selectedLetter.id!!,
                startedAt = event.startedAt,
            )
            userLetterPersistencePort.save(createdLetter)
        } catch (e: Exception) {
            // TODO: Sentry 로 예외 전송
            logger.error(e) { "러닝 완료 후 편지 발송 시간 조정 오류 - userId: ${event.userId}" }
        }
    }
}
