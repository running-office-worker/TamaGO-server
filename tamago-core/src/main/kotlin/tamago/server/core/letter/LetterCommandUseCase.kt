package tamago.server.core.letter

import tamago.server.core.letter.domain.aggregate.Letter

interface LetterCommandUseCase {
    fun createTemplate(title: String, content: String): Letter
}
