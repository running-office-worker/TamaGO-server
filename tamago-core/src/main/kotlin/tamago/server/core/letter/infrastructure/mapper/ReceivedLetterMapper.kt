package tamago.server.core.letter.infrastructure.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.letter.infrastructure.entity.LetterEntity
import tamago.server.core.letter.infrastructure.entity.ReceivedLetterEntity

object ReceivedLetterMapper {
    fun toEntity(receivedLetter: ReceivedLetter, letter: LetterEntity): ReceivedLetterEntity {
        return ReceivedLetterEntity(
            id = receivedLetter.id?.value,
            userId = receivedLetter.userId.value,
            letter = letter,
            readStatus = receivedLetter.readStatus,
        )
    }

    fun toDomain(entity: ReceivedLetterEntity?): ReceivedLetter? {
        if (entity == null) return null

        return ReceivedLetter(
            id = entity.id?.let { ReceivedLetterId(it) },
            userId = UserId(entity.userId),
            letterId = LetterId(entity.letter.id!!),
            readStatus = entity.readStatus,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
