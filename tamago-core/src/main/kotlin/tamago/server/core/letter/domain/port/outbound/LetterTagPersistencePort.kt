package tamago.server.core.letter.domain.port.outbound

import tamago.server.core.letter.domain.enum.LetterTag

interface LetterTagPersistencePort {
    fun saveAll(
        letterId: Long,
        tags: Set<LetterTag>,
    )
}
