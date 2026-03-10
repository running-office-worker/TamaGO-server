package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.UserLetter
import tamago.server.core.letter.domain.port.outbound.UserLetterPersistencePort
import tamago.server.core.letter.domain.vo.LetterId
import java.time.LocalDateTime

@Service
class UserLetterCommandService(
    private val userLetterPersistencePort: UserLetterPersistencePort,
) {
    @Transactional
    fun sendLetters(userLetters: List<UserLetter>): List<UserLetter> {
        userLetters.forEach { it.send() }
        return userLetterPersistencePort.saveAll(userLetters)
    }

    @Transactional
    fun markAsRead(userLetter: UserLetter) {
        userLetter.markAsRead()
        userLetterPersistencePort.save(userLetter)
    }

    @Transactional
    fun scheduleNextLetter(
        userId: UserId,
        letterId: LetterId,
        prevScheduledAt: LocalDateTime,
    ) {
        val nextLetter =
            UserLetter.repeat(
                userId = userId,
                letterId = letterId,
                prevScheduledAt = prevScheduledAt,
            )

        userLetterPersistencePort.save(nextLetter)
    }

    @Transactional
    fun reschedule(userLetter: UserLetter) {
        userLetter.reschedule(LocalDateTime.now())
        userLetterPersistencePort.save(userLetter)
    }

    @Transactional
    fun create(userLetter: UserLetter) {
        userLetterPersistencePort.save(userLetter)
    }
}
