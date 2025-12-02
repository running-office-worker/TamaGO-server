package tamago.server.gateway.security.oauth.client

import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto

data class AppleClientResult(
    val id: String,
    val email: String,
)

fun AppleClientResult.toCommand(provider: OAuthProvider): LoginCommandDto =
    LoginCommandDto(
        externalId = this.id,
        email = this.email,
        provider = provider
    )
