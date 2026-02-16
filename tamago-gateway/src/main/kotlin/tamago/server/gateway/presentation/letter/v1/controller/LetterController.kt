package tamago.server.gateway.presentation.letter.v1.controller

import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tamago.server.core.letter.LetterFacade
import tamago.server.core.letter.LetterQueryUseCase
import tamago.server.core.letter.domain.vo.UserLetterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.letter.v1.api.LetterApi
import tamago.server.gateway.presentation.letter.v1.response.LetterResponse

@RestController
class LetterController(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterFacade: LetterFacade,
) : LetterApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/v1/letters")
    override fun readLetterInbox(@CurrentUser user: User): CustomResponse<LetterResponse> {
        val result = letterQueryUseCase.getLatestReceivedLetter(user.id!!)
            ?: return CustomResponse.ok(null)

        return CustomResponse.ok(
            LetterResponse(
                letterId = result.id.value,
                content = result.content,
                letterStatus = result.letterStatus,
            ),
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/letters/read")
    override fun markAsReadLetter(
        @CurrentUser user: User,
        @RequestParam @Parameter(required = true) letterId: UserLetterId,
    ): CustomResponse<Void> {
        letterFacade.markAsRead(user.id!!, letterId)
        return CustomResponse.noContent()
    }
}
