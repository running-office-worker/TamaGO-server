package tamago.server.core.letter

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.application.validator.LetterValidator
import tamago.server.core.letter.domain.vo.ReceivedLetterId

@Component
class LetterFacade(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterCommandUseCase: LetterCommandUseCase,
    private val letterValidator: LetterValidator,
) {
    @Transactional
    fun markAsRead(userId: UserId, receivedLetterId: ReceivedLetterId) {
        val receivedLetter = letterQueryUseCase.get(receivedLetterId)
        letterValidator.validateOwner(userId, receivedLetter)
        letterCommandUseCase.markAsRead(receivedLetter)
    }
}
