package tamago.server.core.letter.infrastructure.repository

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort
import tamago.server.core.letter.domain.port.outbound.query.LetterInboxQueryModel
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.infrastructure.entity.LetterEntity
import tamago.server.core.letter.infrastructure.entity.LetterTagEntity
import tamago.server.core.letter.infrastructure.entity.UserLetterEntity
import tamago.server.core.letter.infrastructure.mapper.LetterMapper

@Repository
class LetterPersistenceAdapter(
    private val letterJpaRepository: LetterJpaRepository,
) : LetterPersistencePort {
    override fun save(letter: Letter): Letter =
        LetterMapper.toDomain(letterJpaRepository.save(LetterMapper.toEntity(letter)))!!

    override fun findById(id: LetterId): Letter? =
        LetterMapper.toDomain(letterJpaRepository.findById(id.value).orElse(null))

    override fun findAllActive(): List<Letter> =
        letterJpaRepository.findAllByDeletedAtIsNull().mapNotNull { LetterMapper.toDomain(it) }

    override fun findActiveByTag(tag: LetterTag): List<Letter> =
        letterJpaRepository
            .findAll {
                select(entity(LetterEntity::class))
                    .from(
                        entity(LetterEntity::class),
                        join(LetterTagEntity::class).on(
                            path(LetterTagEntity::letterId).equal(path(LetterEntity::id)),
                        ),
                    ).where(
                        and(
                            path(LetterTagEntity::tag).equal(tag),
                            path(LetterEntity::deletedAt).isNull(),
                            path(LetterTagEntity::deletedAt).isNull(),
                        ),
                    )
            }.filterNotNull()
            .mapNotNull { LetterMapper.toDomain(it) }

    override fun findLatestByUserId(userId: UserId): LetterInboxQueryModel? =
        letterJpaRepository
            .findPage(PageRequest.of(0, 1)) {
                selectNew<LetterInboxQueryModel>(
                    path(UserLetterEntity::id),
                    path(LetterEntity::title),
                    path(LetterEntity::content),
                    path(UserLetterEntity::letterStatus),
                ).from(
                    entity(UserLetterEntity::class),
                    join(UserLetterEntity::letter),
                ).where(
                    and(
                        path(UserLetterEntity::userId).eq(userId.value),
                        path(UserLetterEntity::letterStatus).ne(LetterStatus.SCHEDULED),
                    ),
                ).orderBy(
                    path(UserLetterEntity::createdAt).desc(),
                )
            }.content
            .firstOrNull()
}
