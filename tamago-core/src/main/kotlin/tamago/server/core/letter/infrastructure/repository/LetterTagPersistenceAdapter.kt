package tamago.server.core.letter.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.port.outbound.LetterTagPersistencePort
import tamago.server.core.letter.infrastructure.mapper.LetterMapper

@Repository
class LetterTagPersistenceAdapter(
    private val letterTagJpaRepository: LetterTagJpaRepository,
) : LetterTagPersistencePort {
    override fun saveAll(
        letterId: Long,
        tags: Set<LetterTag>,
    ) {
        val tagEntities = LetterMapper.toTagEntities(letterId, tags)
        letterTagJpaRepository.saveAll(tagEntities)
    }
}
