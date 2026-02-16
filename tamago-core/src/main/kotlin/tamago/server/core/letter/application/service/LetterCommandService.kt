package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.letter.LetterCommandUseCase
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort

@Service
class LetterCommandService(
    private val letterPersistencePort: LetterPersistencePort,
) : LetterCommandUseCase {

    @Transactional
    override fun createTemplate(
        title: String,
        content: String,
    ): Letter {
        val letter = Letter.create(title, content)
        return letterPersistencePort.save(letter)
    }
}
