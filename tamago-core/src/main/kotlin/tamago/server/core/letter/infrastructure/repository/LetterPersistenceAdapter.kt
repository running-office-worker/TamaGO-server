package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort
import tamago.server.core.letter.domain.port.outbound.query.LetterInboxQueryModel
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.infrastructure.entity.LetterEntity
import tamago.server.core.letter.infrastructure.entity.ReceivedLetterEntity
import tamago.server.core.letter.infrastructure.mapper.LetterMapper
import tamago.server.core.common.vo.UserId

@Repository
class LetterPersistenceAdapter(
    private val letterJpaRepository: LetterJpaRepository,
) : LetterPersistencePort {
    override fun save(letter: Letter): Letter =
        LetterMapper.toDomain(letterJpaRepository.save(LetterMapper.toEntity(letter)))!!

    override fun findById(id: LetterId): Letter? =
        LetterMapper.toDomain(letterJpaRepository.findById(id.value).orElse(null))

    override fun findAll(): List<Letter> =
        letterJpaRepository.findAll().mapNotNull { LetterMapper.toDomain(it) }

    override fun findTopReceivedLetterByUserId(userId: UserId): LetterInboxQueryModel? {
        return letterJpaRepository.findPage(PageRequest.of(0, 1)) {
            selectNew<LetterInboxQueryModel>(
                path(ReceivedLetterEntity::id),
                path(LetterEntity::title),
                path(LetterEntity::content),
                path(ReceivedLetterEntity::readStatus),
            ).from(
                entity(ReceivedLetterEntity::class),
                join(ReceivedLetterEntity::letter),
            ).where(
                path(ReceivedLetterEntity::userId).eq(userId.value)
            ).orderBy(
                path(ReceivedLetterEntity::createdAt).desc()
            )
        }.content.firstOrNull()
    }
}
