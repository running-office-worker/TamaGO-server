package tamago.server.storage.letter.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.UserLetterId
import tamago.server.storage.letter.entity.LetterEntity
import tamago.server.storage.letter.entity.UserLetterEntity

object UserLetterMapper {
    fun toEntity(
        userLetter: UserLetter,
        letter: LetterEntity,
    ): UserLetterEntity {
        val entity =
            UserLetterEntity(
                id = userLetter.id?.value,
                userId = userLetter.userId.value,
                letter = letter,
                letterStatus = userLetter.letterStatus,
                scheduledAt = userLetter.scheduledAt,
                deletedAt = userLetter.deletedAt,
            )
        return entity
    }

    fun toDomain(entity: UserLetterEntity?): UserLetter? {
        if (entity == null) return null

        return UserLetter(
            id = entity.id?.let { UserLetterId(it) },
            userId = UserId(entity.userId),
            letterId = LetterId(entity.letter.id!!),
            letterStatus = entity.letterStatus,
            scheduledAt = entity.scheduledAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
