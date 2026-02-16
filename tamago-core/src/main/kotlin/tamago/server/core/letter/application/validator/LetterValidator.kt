package tamago.server.core.letter.application.validator

import org.springframework.stereotype.Component
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.application.exception.LetterAccessDeniedException
import tamago.server.core.letter.domain.aggregate.UserLetter

@Component
class LetterValidator {
    fun validateOwner(userId: UserId, userLetter: UserLetter) {
        if (!userLetter.isOwnedBy(userId)) {
            throw LetterAccessDeniedException()
        }
    }
}
