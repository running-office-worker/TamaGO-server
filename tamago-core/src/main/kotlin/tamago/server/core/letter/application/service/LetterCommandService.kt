package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.port.inbound.command.CreateLetterCommand
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort

@Service
class LetterCommandService(
    private val letterPersistencePort: LetterPersistencePort,
) {
    @Transactional
    fun createTemplates(commands: List<CreateLetterCommand>): List<Letter> =
        commands.map { command ->
            val letter = Letter.create(command.title, command.content, command.tags)
            letterPersistencePort.save(letter)
        }
}
