package tamago.server.gateway.presentation.letter.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class LetterCreateRequest(
    @field:Schema(description = "편지 제목", example = "템플릿 제목", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotBlank(message = "제목은 필수입니다.")
    val title: String,

    @field:Schema(description = "편지 내용", example = "오늘도 열심히 달린 당신, 정말 대단해요!", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotBlank(message = "내용은 필수입니다.")
    val content: String,
)
