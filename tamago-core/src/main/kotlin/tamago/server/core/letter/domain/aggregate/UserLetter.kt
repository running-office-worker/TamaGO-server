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
    scheduledAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var letterStatus: LetterStatus = letterStatus
        private set

    var scheduledAt: LocalDateTime? = scheduledAt
        private set

    fun send() {
        this.letterStatus = LetterStatus.UNREAD
    }

    fun markAsRead() {
        this.letterStatus = LetterStatus.READ
    }

    fun reschedule(baseTime: LocalDateTime) {
        this.scheduledAt = baseTime.plusHours(INITIAL_DELAY_HOURS)
    }

    fun isOwnedBy(userId: UserId): Boolean = this.userId == userId

    companion object {
        private const val INITIAL_DELAY_HOURS = 23L
        private const val REPEAT_INTERVAL_HOURS = 24L

        fun new(userId: UserId, letterId: LetterId, startedAt: LocalDateTime): UserLetter {
            return UserLetter(
                userId = userId,
                letterId = letterId,
                letterStatus = LetterStatus.SCHEDULED,
                scheduledAt = startedAt.plusHours(INITIAL_DELAY_HOURS),
            )
        }

        fun repeat(userId: UserId, letterId: LetterId, prevScheduledAt: LocalDateTime): UserLetter {
            return UserLetter(
                userId = userId,
                letterId = letterId,
                letterStatus = LetterStatus.SCHEDULED,
                scheduledAt = prevScheduledAt.plusHours(REPEAT_INTERVAL_HOURS),
            )
        }
    }
}
