package tamago.server.gateway.presentation.letter.v1.controller

import io.swagger.v3.oas.annotations.Parameter
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tamago.server.core.letter.LetterCommandUseCase
import tamago.server.core.letter.LetterFacade
import tamago.server.core.letter.LetterQueryUseCase
import tamago.server.core.letter.domain.vo.ReceivedLetterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.presentation.letter.v1.api.LetterApi
import tamago.server.gateway.presentation.letter.v1.request.LetterCreate
import tamago.server.gateway.presentation.letter.v1.response.LetterResponse
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.annotation.CurrentUser

@RestController
class LetterController(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterCommandUseCase: LetterCommandUseCase,
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
                readStatus = result.readStatus,
            ),
        )
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/letters/read")
    override fun markAsReadLetter(
        @CurrentUser user: User,
        @RequestParam @Parameter(required = true) letterId: ReceivedLetterId,
    ): CustomResponse<Void> {
        letterFacade.markAsRead(letterId)
        return CustomResponse.noContent()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/letters")
    override fun createTemplate(@RequestBody @Valid request: LetterCreate): CustomResponse<Void> {
        letterCommandUseCase.createTemplate(
            title = request.title,
            content = request.content,
            mood = request.mood
        )

        return CustomResponse.created()
    }
}
