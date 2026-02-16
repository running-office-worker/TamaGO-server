package tamago.server.core.letter

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.port.inbound.query.LetterInboxDto

interface LetterQueryUseCase {
    fun getLatestLetter(userId: UserId): LetterInboxDto?
}
