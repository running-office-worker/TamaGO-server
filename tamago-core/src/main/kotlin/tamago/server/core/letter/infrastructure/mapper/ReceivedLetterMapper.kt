package tamago.server.core.letter.infrastructure.mapper

import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.letter.infrastructure.entity.ReceivedLetterEntity
import tamago.server.core.common.vo.UserId

object ReceivedLetterMapper {
    fun toEntity(receivedLetter: ReceivedLetter): ReceivedLetterEntity {
        return ReceivedLetterEntity(
            id = receivedLetter.id?.value,
            userId = receivedLetter.userId.value,
            letterId = receivedLetter.letterId.value,
            readStatus = receivedLetter.readStatus,
        )
    }

    fun toDomain(entity: ReceivedLetterEntity?): ReceivedLetter? {
        if (entity == null) return null

        return ReceivedLetter(
            id = entity.id?.let { ReceivedLetterId(it) },
            userId = UserId(entity.userId),
            letterId = LetterId(entity.letterId),
            readStatus = entity.readStatus,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
