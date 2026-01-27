package tamago.server.gateway.presentation.user.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class NicknameRequest(
    @field:Schema(description = "닉네임", example = "타마고", requiredMode = Schema.RequiredMode.REQUIRED)
    @field:NotBlank(message = "닉네임은 필수입니다.")
    @field:Size(min = 1, max = 20, message = "닉네임은 1~20자 사이여야 합니다.")
    val nickname: String,
)
