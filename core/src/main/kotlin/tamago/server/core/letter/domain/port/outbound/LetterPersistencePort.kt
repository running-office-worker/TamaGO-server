package tamago.server.core.letter.domain.port.outbound

import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.vo.LetterId

interface LetterPersistencePort {
    fun save(letter: Letter): Letter
    fun findById(id: LetterId): Letter?
    fun findAll(): List<Letter>
}
