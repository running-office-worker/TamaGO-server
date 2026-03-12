package tamago.server.storage.letter.mapper

import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.vo.LetterContent
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.storage.letter.entity.LetterEntity
import tamago.server.storage.letter.entity.LetterTagEntity

object LetterMapper {
    fun toEntity(letter: Letter): LetterEntity =
        LetterEntity(
            id = letter.id?.value,
            title = letter.title,
            content = letter.content.value,
        )

    fun toDomain(
        entity: LetterEntity?,
        tagEntities: List<LetterTagEntity> = emptyList(),
    ): Letter? {
        if (entity == null) return null

        return Letter(
            id = entity.id?.let { LetterId(it) },
            title = entity.title,
            content = LetterContent(entity.content),
            tags = tagEntities.map { it.tag }.toSet(),
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }

    fun toTagEntities(
        letterId: Long,
        tags: Set<LetterTag>,
    ): List<LetterTagEntity> = tags.map { tag -> LetterTagEntity(letterId = letterId, tag = tag) }
}
