package tamago.server.core.letter.domain.port.outbound

import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.vo.LetterId
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.common.vo.UserId

interface ReceivedLetterPersistencePort {
    fun save(receivedLetter: ReceivedLetter): ReceivedLetter
    fun saveAll(receivedLetters: List<ReceivedLetter>): List<ReceivedLetter>
    fun findById(id: ReceivedLetterId): ReceivedLetter?
    fun findByUserId(userId: UserId): List<ReceivedLetter>
    fun findByUserIdAndLetterId(userId: UserId, letterId: LetterId): ReceivedLetter?
    fun findLatestByUserId(userId: UserId): ReceivedLetter?
}
