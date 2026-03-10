package tamago.server.core.letter.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.UserLetterId
import java.time.LocalDateTime

interface UserLetterPersistencePort {
    fun save(userLetter: UserLetter)

    fun saveAll(userLetters: List<UserLetter>): List<UserLetter>

    fun findById(id: UserLetterId): UserLetter?

    fun findByUserId(userId: UserId): List<UserLetter>

    fun findByUserIdAndLetterId(
        userId: UserId,
        letterId: LetterId,
    ): UserLetter?

    fun findLatestByUserId(userId: UserId): UserLetter?

    fun findByUserIdAndLetterStatus(
        userId: UserId,
        letterStatus: LetterStatus,
    ): UserLetter?

    fun findLatestDistinctByUser(): List<UserLetter>

    fun findAllByLetterStatusAndScheduledAtBefore(
        letterStatus: LetterStatus,
        before: LocalDateTime,
    ): List<UserLetter>
}
