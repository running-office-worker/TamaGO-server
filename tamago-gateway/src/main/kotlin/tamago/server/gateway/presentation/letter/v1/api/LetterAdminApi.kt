package tamago.server.gateway.presentation.letter.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.gateway.presentation.letter.v1.request.LetterCreateRequest
import tamago.server.gateway.common.response.CustomResponse

@Tag(name = "🧪Letter API", description = "관리자용 편지 관리 API")
interface LetterAdminApi {
    @Operation(summary = "편지 템플릿 데이터 삽입", description = "편지 템플릿에 데이터를 삽입하여 편지 내용을 생성합니다.")
    fun createTemplate(request: LetterCreateRequest): CustomResponse<Void>
}
