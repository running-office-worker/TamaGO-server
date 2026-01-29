package tamago.server.oauth.client.apple

import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto

data class AppleClientResult(
    val id: String,
    val email: String,
)

fun AppleClientResult.toCommand(provider: AuthProvider): LoginCommandDto =
    LoginCommandDto(
        externalId = this.id,
        email = this.email,
        provider = provider
    )
