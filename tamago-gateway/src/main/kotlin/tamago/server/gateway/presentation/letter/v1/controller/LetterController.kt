package tamago.server.gateway.presentation.letter.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.letter.LetterCommandUseCase
import tamago.server.core.letter.LetterFacade
import tamago.server.core.letter.LetterQueryUseCase
import tamago.server.core.letter.domain.vo.UserLetterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.letter.v1.request.LetterCreateRequest
import tamago.server.gateway.presentation.letter.v1.response.LetterResponse

@Tag(name = "Letter API", description = "타마고의 편지 API")
@RestController
class LetterController(
    private val letterQueryUseCase: LetterQueryUseCase,
    private val letterCommandUseCase: LetterCommandUseCase,
    private val letterFacade: LetterFacade,
) {
    @Operation(summary = "편지 수신함 조회", description = "가장 최근 받은 편지 한 개를 조회합니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/v1/letters")
    fun readLetterInbox(
        @CurrentUser user: User,
    ): CustomResponse<LetterResponse> {
        val result =
            letterQueryUseCase.getLatestLetter(user.id!!)
                ?: return CustomResponse.ok(null)

        return CustomResponse.ok(
            LetterResponse(
                letterId = result.id.value,
                content = result.content,
                letterStatus = result.letterStatus,
            ),
        )
    }

    @Operation(summary = "편지 수신 확인", description = "가장 최근 받은 편지를 읽음 처리합니다.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/letters/read")
    fun markAsReadLetter(
        @CurrentUser user: User,
        @RequestParam @Parameter(required = true) letterId: UserLetterId,
    ): CustomResponse<Void> {
        letterFacade.markAsRead(user.id!!, letterId)
        return CustomResponse.noContent()
    }

    @Operation(summary = "\uD83E\uDDEA 편지 템플릿 데이터 삽입", description = "편지 템플릿에 데이터를 삽입하여 편지 내용을 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/letters")
    fun createTemplate(
        @RequestBody @Valid request: LetterCreateRequest,
    ): CustomResponse<Void> {
        letterCommandUseCase.createTemplate(
            title = request.title,
            content = request.content,
        )

        return CustomResponse.created()
    }
}
