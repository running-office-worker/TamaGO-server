package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.port.outbound.LetterTagPersistencePort

@Service
class LetterTagCommandService(
    private val letterTagPersistencePort: LetterTagPersistencePort,
) {
    @Transactional
    fun saveAll(
        letterId: Long,
        tags: Set<LetterTag>,
    ) {
        letterTagPersistencePort.saveAll(letterId, tags)
    }
}
