package tamago.server.core.letter.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.port.outbound.query.LetterInboxQueryModel
import tamago.server.core.letter.domain.vo.LetterId

interface LetterPersistencePort {
    fun save(letter: Letter): Letter
    fun findById(id: LetterId): Letter?
    fun findAllActive(): List<Letter>
    fun findTopUserLetterByUserId(userId: UserId): LetterInboxQueryModel?
}
