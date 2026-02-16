package tamago.server.core.letter.application.event

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.running.domain.event.RunningCompletedEvent
import tamago.server.core.letter.application.service.LetterQueryService
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.port.outbound.UserLetterPersistencePort
import java.time.LocalDateTime

@Component
class LetterEventListener(
    private val letterQueryService: LetterQueryService,
    private val userLetterPersistencePort: UserLetterPersistencePort,
) {
    @Transactional
    @TransactionalEventListener
    fun onRunningCompleted(event: RunningCompletedEvent) {
        userLetterPersistencePort.findByUserIdAndLetterStatus(event.userId, LetterStatus.SCHEDULED)?.let {
            it.reschedule(LocalDateTime.now())
            userLetterPersistencePort.save(it)
            return
        }

        val selectedLetter = letterQueryService.getRandomTemplate()

        val createdLetter = UserLetter.new(
            userId = event.userId,
            letterId = selectedLetter.id!!,
            startedAt = event.startedAt,
        )
        userLetterPersistencePort.save(createdLetter)
    }
}
