package tamago.server.core.letter

import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.port.inbound.query.LetterInboxDto
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.common.vo.UserId

interface LetterQueryUseCase {
    fun getLatestReceivedLetter(userId: UserId): LetterInboxDto?
    fun get(id: ReceivedLetterId): ReceivedLetter
}
