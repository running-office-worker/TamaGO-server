package tamago.server.gateway.security.oauth.client

import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto

data class KakaoClientResult(
    val externalId: String,
    val email: String,
)

fun KakaoClientResult.toCommand(provider: AuthProvider): LoginCommandDto =
    LoginCommandDto(
        externalId = this.externalId,
        email = this.email,
        provider = provider
    )
