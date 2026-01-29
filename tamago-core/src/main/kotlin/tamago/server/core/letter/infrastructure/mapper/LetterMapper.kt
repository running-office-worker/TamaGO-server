package tamago.server.core.letter.infrastructure.mapper

import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.vo.LetterContent
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.infrastructure.entity.LetterEntity

object LetterMapper {
    fun toEntity(letter: Letter): LetterEntity {
        return LetterEntity(
            id = letter.id?.value,
            title = letter.title,
            content = letter.content.value,
            mood = letter.mood,
        )
    }

    fun toDomain(entity: LetterEntity?): Letter? {
        if (entity == null) return null

        return Letter(
            id = entity.id?.let { LetterId(it) },
            title = entity.title,
            content = LetterContent(entity.content),
            mood = entity.mood,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
