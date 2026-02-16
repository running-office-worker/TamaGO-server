package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.port.outbound.UserLetterPersistencePort
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.UserLetterId
import tamago.server.core.letter.infrastructure.entity.UserLetterEntity
import tamago.server.core.letter.infrastructure.mapper.UserLetterMapper
import java.time.LocalDateTime

@Repository
class UserLetterPersistenceAdapter(
    private val userLetterJpaRepository: UserLetterJpaRepository,
    private val letterJpaRepository: LetterJpaRepository,
) : UserLetterPersistencePort {
    override fun save(userLetter: UserLetter): UserLetter {
        val letterEntity = letterJpaRepository.findByIdOrNull(userLetter.letterId.value)
            ?: throw IllegalArgumentException("Letter not found: ${userLetter.letterId}")
        return UserLetterMapper.toDomain(
            userLetterJpaRepository.save(UserLetterMapper.toEntity(userLetter, letterEntity)),
        )!!
    }

    override fun saveAll(userLetters: List<UserLetter>): List<UserLetter> {
        val letterIds = userLetters.map { it.letterId.value }.distinct()
        val letterMap = letterJpaRepository.findAllById(letterIds).associateBy { it.id }
        return userLetterJpaRepository.saveAll(
            userLetters.map { ul ->
                val letterEntity = letterMap[ul.letterId.value]
                    ?: throw IllegalArgumentException("Letter not found: ${ul.letterId}")
                UserLetterMapper.toEntity(ul, letterEntity)
            },
        ).mapNotNull { UserLetterMapper.toDomain(it) }
    }

    override fun findById(id: UserLetterId): UserLetter? =
        UserLetterMapper.toDomain(userLetterJpaRepository.findByIdOrNull(id.value))

    override fun findByUserId(userId: UserId): List<UserLetter> =
        userLetterJpaRepository.findByUserId(userId.value).mapNotNull { UserLetterMapper.toDomain(it) }

    override fun findByUserIdAndLetterId(userId: UserId, letterId: LetterId): UserLetter? =
        UserLetterMapper.toDomain(userLetterJpaRepository.findByUserIdAndLetterId(userId.value, letterId.value))

    override fun findLatestByUserId(userId: UserId): UserLetter? =
        UserLetterMapper.toDomain(userLetterJpaRepository.findTopByUserIdOrderByCreatedAtDesc(userId.value))

    override fun findByUserIdAndLetterStatus(userId: UserId, letterStatus: LetterStatus): UserLetter? =
        UserLetterMapper.toDomain(userLetterJpaRepository.findByUserIdAndLetterStatus(userId.value, letterStatus))

    override fun findLatestNotScheduledDistinctByUser(): List<UserLetter> =
        userLetterJpaRepository.findAll {
            select(entity(UserLetterEntity::class))
                .from(entity(UserLetterEntity::class))
                .where(
                    path(UserLetterEntity::letterStatus).ne(LetterStatus.SCHEDULED),
                ).orderBy(
                    path(UserLetterEntity::userId).asc(),
                    path(UserLetterEntity::createdAt).desc(),
                )
        }.filterNotNull()
            .distinctBy { it.userId }
            .mapNotNull { UserLetterMapper.toDomain(it) }
}
