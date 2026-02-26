package tamago.server.gateway.presentation.letter.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.letter.domain.enum.LetterStatus

data class LetterResponse(
    @field:Schema(description = "편지 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val letterId: Long,
    @field:Schema(description = "편지 내용", example = "안녕하세요, 타마고입니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    val content: String,
    @field:Schema(description = "읽음 상태", example = "UNREAD", requiredMode = Schema.RequiredMode.REQUIRED)
    val letterStatus: LetterStatus,
)
