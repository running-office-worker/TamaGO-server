package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
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
    fun get(id: UserLetterId): UserLetter {
        return userLetterPersistencePort.findById(id)
            ?: throw UserLetterNotFoundException()
    }

    fun getAllUsersLastSentLetter(): List<UserLetter> {
        return userLetterPersistencePort.findLatestDistinctByUser()
            .filter { it.letterStatus != LetterStatus.SCHEDULED }
    }
}
