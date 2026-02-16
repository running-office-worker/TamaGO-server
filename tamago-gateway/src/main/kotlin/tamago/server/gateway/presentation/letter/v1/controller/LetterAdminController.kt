package tamago.server.gateway.presentation.letter.v1.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tamago.server.core.letter.LetterCommandUseCase
import tamago.server.gateway.presentation.letter.v1.api.LetterAdminApi
import tamago.server.gateway.presentation.letter.v1.request.LetterCreateRequest
import tamago.server.gateway.common.response.CustomResponse

@RestController
class LetterAdminController(
    private val letterCommandUseCase: LetterCommandUseCase,
) : LetterAdminApi {

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/letters")
    override fun createTemplate(@RequestBody @Valid request: LetterCreateRequest): CustomResponse<Void> {
        letterCommandUseCase.createTemplate(
            title = request.title,
            content = request.content,
        )

        return CustomResponse.created()
    }
}
