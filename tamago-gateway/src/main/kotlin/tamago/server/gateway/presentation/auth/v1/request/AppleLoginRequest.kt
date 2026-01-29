package tamago.server.gateway.presentation.auth.v1.request

import io.swagger.v3.oas.annotations.media.Schema

data class AppleLoginRequest(
    @field:Schema(description = "Apple SDK identity token", example = "identity_token only")
    val token: String,
)