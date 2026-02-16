package tamago.server.core.letter.domain.port.inbound.query

import tamago.server.core.letter.domain.enum.LetterStatus
import tamago.server.core.letter.domain.vo.UserLetterId

data class LetterInboxDto(
    val id: UserLetterId,
    val title: String,
    val content: String,
    val letterStatus: LetterStatus,
)
