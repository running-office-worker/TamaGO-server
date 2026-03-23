package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.application.exception.UserLetterNotFoundException
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.port.outbound.UserLetterPersistencePort
import tamago.server.core.letter.domain.vo.UserLetterId
import java.time.LocalDateTime

@Service
class UserLetterQueryService(
    private val userLetterPersistencePort: UserLetterPersistencePort,
) {
    fun get(id: UserLetterId): UserLetter =
        userLetterPersistencePort.findById(id)
            ?: throw UserLetterNotFoundException()

    fun getAllUsersLastSentLetter(): List<UserLetter> =
        userLetterPersistencePort
            .findLatestDistinctByUser()
            .filter { it.letterStatus != LetterStatus.SCHEDULED }

    fun getScheduledLettersBeforeOrEqual(now: LocalDateTime): List<UserLetter> =
        userLetterPersistencePort.findAllByLetterStatusAndScheduledAtBeforeOrEqual(LetterStatus.SCHEDULED, now)

    fun findByUserId(userId: UserId): List<UserLetter> = userLetterPersistencePort.findByUserId(userId)

    fun findByUserIdAndLetterStatus(
        userId: UserId,
        letterStatus: LetterStatus,
    ): UserLetter? = userLetterPersistencePort.findByUserIdAndLetterStatus(userId, letterStatus)

    fun findAllDeletedByUserId(userId: UserId): List<UserLetter> =
        userLetterPersistencePort.findAllDeletedByUserId(userId)
}
