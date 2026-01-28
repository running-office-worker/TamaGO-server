package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import tamago.server.core.letter.LetterCommandUseCase
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.port.outbound.ReceivedLetterPersistencePort

@Service
class LetterCommandService(
    private val receivedLetterPersistencePort: ReceivedLetterPersistencePort,
) : LetterCommandUseCase {

    override fun markAsRead(receivedLetter: ReceivedLetter) {
        receivedLetter.markAsRead()
        receivedLetterPersistencePort.save(receivedLetter)
    }
}
