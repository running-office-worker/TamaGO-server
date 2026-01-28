package tamago.server.core.letter.domain.aggregate

import tamago.server.core.letter.domain.enum.ReadStatus
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class ReceivedLetter(
    val id: ReceivedLetterId? = null,
    val userId: UserId,
    val letterId: LetterId,
    readStatus: ReadStatus = ReadStatus.UNREAD,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var readStatus: ReadStatus = readStatus
        private set

    fun markAsRead() {
        this.readStatus = ReadStatus.READ
    }

    fun isOwnedBy(userId: UserId): Boolean = this.userId == userId

    companion object {
        fun create(userId: UserId, letterId: LetterId): ReceivedLetter {
            return ReceivedLetter(
                userId = userId,
                letterId = letterId,
                readStatus = ReadStatus.UNREAD,
            )
        }
    }
}
