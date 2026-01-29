package tamago.server.core.letter.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.port.outbound.ReceivedLetterPersistencePort
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.letter.infrastructure.mapper.ReceivedLetterMapper
import tamago.server.core.common.vo.UserId

@Repository
class ReceivedLetterPersistenceAdapter(
    private val receivedLetterJpaRepository: ReceivedLetterJpaRepository
) : ReceivedLetterPersistencePort {
    override fun save(receivedLetter: ReceivedLetter): ReceivedLetter =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.save(ReceivedLetterMapper.toEntity(receivedLetter)))!!

    override fun saveAll(receivedLetters: List<ReceivedLetter>): List<ReceivedLetter> =
        receivedLetterJpaRepository.saveAll(receivedLetters.map { ReceivedLetterMapper.toEntity(it) })
            .mapNotNull { ReceivedLetterMapper.toDomain(it) }

    override fun findById(id: ReceivedLetterId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findById(id.value).orElse(null))

    override fun findByUserId(userId: UserId): List<ReceivedLetter> =
        receivedLetterJpaRepository.findByUserId(userId.value).mapNotNull { ReceivedLetterMapper.toDomain(it) }

    override fun findByUserIdAndLetterId(userId: UserId, letterId: LetterId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findByUserIdAndLetterId(userId.value, letterId.value))

    override fun findLatestByUserId(userId: UserId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findTopByUserIdOrderByCreatedAtDesc(userId.value))
}
