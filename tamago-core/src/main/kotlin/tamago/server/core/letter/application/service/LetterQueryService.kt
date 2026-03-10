package tamago.server.core.letter.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.LetterQueryUseCase
import tamago.server.core.letter.application.exception.LetterNotFoundException
import tamago.server.core.letter.domain.aggregate.Letter
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.port.inbound.query.LetterInboxDto
import tamago.server.core.letter.domain.port.outbound.LetterPersistencePort

@Service
class LetterQueryService(
    private val letterPersistencePort: LetterPersistencePort,
) : LetterQueryUseCase {
    override fun getLatestLetter(userId: UserId): LetterInboxDto? {
        val query = letterPersistencePort.findLatestByUserId(userId)

        return query?.let {
            LetterInboxDto(
                id = it.id,
                title = it.title,
                content = it.content,
                letterStatus = it.letterStatus,
            )
        }
    }

    fun getRandomTemplate(): Letter =
        letterPersistencePort.findAllActive().randomOrNull()
            ?: throw LetterNotFoundException()

    fun getRandomTemplateByTag(tag: LetterTag): Letter =
        letterPersistencePort.findActiveByTag(tag).randomOrNull()
            ?: getRandomTemplate()
}
