package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import tamago.server.core.letter.LetterQueryUseCase
import tamago.server.core.letter.application.exception.ReceivedLetterNotFoundException
import tamago.server.core.letter.domain.aggregate.ReceivedLetter
import tamago.server.core.letter.domain.port.inbound.query.LetterInboxDto
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort
import tamago.server.core.letter.domain.port.outbound.ReceivedLetterPersistencePort
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.user.domain.vo.UserId

@Service
class LetterQueryService(
    private val letterPersistencePort: LetterPersistencePort,
    private val receivedLetterPersistencePort: ReceivedLetterPersistencePort,
) : LetterQueryUseCase {

    override fun getLatestReceivedLetter(userId: UserId): LetterInboxDto? {
        val query = letterPersistencePort.findTopReceivedLetterByUserId(userId)

        return query?.let {
            LetterInboxDto(
                id = it.id,
                title = it.title,
                content = it.content,
                readStatus = it.readStatus,
            )
        }
    }

    override fun get(id: ReceivedLetterId): ReceivedLetter {
        return receivedLetterPersistencePort.findById(id)
            ?: throw ReceivedLetterNotFoundException()
    }
}
