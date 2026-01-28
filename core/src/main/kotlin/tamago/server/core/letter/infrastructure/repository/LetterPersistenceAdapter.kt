package tamago.server.core.letter.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.infrastructure.mapper.LetterMapper

@Repository
class LetterPersistenceAdapter(
    private val letterJpaRepository: LetterJpaRepository
) : LetterPersistencePort {
    override fun save(letter: Letter): Letter =
        LetterMapper.toDomain(letterJpaRepository.save(LetterMapper.toEntity(letter)))!!

    override fun findById(id: LetterId): Letter? =
        LetterMapper.toDomain(letterJpaRepository.findById(id.value).orElse(null))

    override fun findAll(): List<Letter> =
        letterJpaRepository.findAll().mapNotNull { LetterMapper.toDomain(it) }
}
