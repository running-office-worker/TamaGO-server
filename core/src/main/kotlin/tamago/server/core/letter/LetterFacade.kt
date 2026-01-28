package tamago.server.core.letter

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.letter.domain.vo.ReceivedLetterId

@Component
class LetterFacade(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterCommandUseCase: LetterCommandUseCase,
) {
    @Transactional
    fun markAsRead(receivedLetterId: ReceivedLetterId) {
        val receivedLetter = letterQueryUseCase.get(receivedLetterId)
        letterCommandUseCase.markAsRead(receivedLetter)
    }
}
