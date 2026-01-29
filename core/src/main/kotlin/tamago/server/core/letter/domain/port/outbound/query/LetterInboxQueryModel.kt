package tamago.server.core.letter.domain.port.outbound.query

import tamago.server.core.letter.domain.enum.ReadStatus
import tamago.server.core.letter.domain.vo.ReceivedLetterId

data class LetterInboxQueryModel(
    val id: ReceivedLetterId,
    val title: String,
    val content: String,
    val readStatus: ReadStatus,
)
