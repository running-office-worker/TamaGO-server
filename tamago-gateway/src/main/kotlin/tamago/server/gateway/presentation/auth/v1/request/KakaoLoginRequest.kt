package tamago.server.gateway.presentation.auth.v1.request

import io.swagger.v3.oas.annotations.media.Schema

data class KakaoLoginRequest(
    @field:Schema(description = "Kakao SDK access token", example = "access_token only")
    val token: String,
)