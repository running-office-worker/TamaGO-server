package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.port.outbound.ReceivedLetterPersistencePort
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.letter.infrastructure.mapper.ReceivedLetterMapper

@Repository
class ReceivedLetterPersistenceAdapter(
    private val receivedLetterJpaRepository: ReceivedLetterJpaRepository,
    private val letterJpaRepository: LetterJpaRepository,
) : ReceivedLetterPersistencePort {
    override fun save(receivedLetter: ReceivedLetter): ReceivedLetter {
        val letterEntity = letterJpaRepository.findByIdOrNull(receivedLetter.letterId.value)
            ?: throw IllegalArgumentException("Letter not found: ${receivedLetter.letterId}")
        return ReceivedLetterMapper.toDomain(
            receivedLetterJpaRepository.save(ReceivedLetterMapper.toEntity(receivedLetter, letterEntity)),
        )!!
    }

    override fun saveAll(receivedLetters: List<ReceivedLetter>): List<ReceivedLetter> {
        val letterIds = receivedLetters.map { it.letterId.value }.distinct()
        val letterMap = letterJpaRepository.findAllById(letterIds).associateBy { it.id }
        return receivedLetterJpaRepository.saveAll(
            receivedLetters.map { rl ->
                val letterEntity = letterMap[rl.letterId.value]
                    ?: throw IllegalArgumentException("Letter not found: ${rl.letterId}")
                ReceivedLetterMapper.toEntity(rl, letterEntity)
            },
        ).mapNotNull { ReceivedLetterMapper.toDomain(it) }
    }

    override fun findById(id: ReceivedLetterId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findByIdOrNull(id.value))

    override fun findByUserId(userId: UserId): List<ReceivedLetter> =
        receivedLetterJpaRepository.findByUserId(userId.value).mapNotNull { ReceivedLetterMapper.toDomain(it) }

    override fun findByUserIdAndLetterId(userId: UserId, letterId: LetterId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findByUserIdAndLetterId(userId.value, letterId.value))

    override fun findLatestByUserId(userId: UserId): ReceivedLetter? =
        ReceivedLetterMapper.toDomain(receivedLetterJpaRepository.findTopByUserIdOrderByCreatedAtDesc(userId.value))
}
