package tamago.server.core.letter.application.validator

import org.springframework.stereotype.Component
import tamago.server.core.letter.application.exception.LetterAccessDeniedException
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.user.domain.vo.UserId

@Component
class LetterValidator {
    fun validateOwner(userId: UserId, receivedLetter: ReceivedLetter) {
        if (!receivedLetter.isOwnedBy(userId)) {
            throw LetterAccessDeniedException()
        }
    }
}
