package tamago.server.core.letter

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.letter.application.validator.LetterValidator
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.user.domain.aggregate.User

@Component
class LetterFacade(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterCommandUseCase: LetterCommandUseCase,
    private val letterValidator: LetterValidator,
) {
    @Transactional
    fun markAsRead(user: User, receivedLetterId: ReceivedLetterId) {
        val receivedLetter = letterQueryUseCase.get(receivedLetterId)
        letterValidator.validateOwner(user.id!!, receivedLetter)
        letterCommandUseCase.markAsRead(receivedLetter)
    }
}
