package tamago.server.gateway.presentation.auth.v1.request

import io.swagger.v3.oas.annotations.media.Schema

data class TokenRequest(
    @field:Schema(description = "OAuth provider 제공 토큰", example = "id_token or access_token")
    val token: String,
)