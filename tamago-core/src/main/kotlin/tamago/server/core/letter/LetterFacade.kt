package tamago.server.core.letter

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.letter.application.service.UserLetterCommandService
import tamago.server.core.letter.application.service.UserLetterQueryService
import tamago.server.core.letter.application.validator.LetterValidator
import tamago.server.core.letter.domain.vo.UserLetterId

@Component
class LetterFacade(
    private val userLetterQueryService: UserLetterQueryService,
    private val userLetterCommandService: UserLetterCommandService,
    private val letterValidator: LetterValidator,
) {
    @Transactional
    fun markAsRead(
        userId: UserId,
        userLetterId: UserLetterId,
    ) {
        val userLetter = userLetterQueryService.get(userLetterId)
        letterValidator.validateOwner(userId, userLetter)
        userLetterCommandService.markAsRead(userLetter)
    }
}
