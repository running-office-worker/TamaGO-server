package tamago.server.core.letter.domain.aggregate

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.UserLetterId
import java.time.LocalDateTime

class UserLetter(
    val id: UserLetterId? = null,
    val userId: UserId,
    val letterId: LetterId,
    letterStatus: LetterStatus = LetterStatus.UNREAD,
    val scheduledAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var letterStatus: LetterStatus = letterStatus
        private set

    fun deliver() {
        this.letterStatus = LetterStatus.UNREAD
    }

    fun markAsRead() {
        this.letterStatus = LetterStatus.READ
    }

    fun isOwnedBy(userId: UserId): Boolean = this.userId == userId

    companion object {
        private const val INITIAL_DELAY_HOURS = 23L
        private const val REPEAT_INTERVAL_HOURS = 24L

        fun createScheduled(userId: UserId, letterId: LetterId, startedAt: LocalDateTime): UserLetter {
            return UserLetter(
                userId = userId,
                letterId = letterId,
                letterStatus = LetterStatus.SCHEDULED,
                scheduledAt = startedAt.plusHours(INITIAL_DELAY_HOURS),
            )
        }

        fun createNextScheduled(userId: UserId, letterId: LetterId, previousScheduledAt: LocalDateTime): UserLetter {
            return UserLetter(
                userId = userId,
                letterId = letterId,
                letterStatus = LetterStatus.SCHEDULED,
                scheduledAt = previousScheduledAt.plusHours(REPEAT_INTERVAL_HOURS),
            )
        }

        fun create(userId: UserId, letterId: LetterId): UserLetter {
            return UserLetter(userId = userId, letterId = letterId)
        }
    }
}
