package tamago.server.core.letter

import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.aggregate.ReceivedLetter

interface LetterCommandUseCase {
    fun markAsRead(receivedLetter: ReceivedLetter)
    fun createTemplate(title: String, content: String): Letter
}
