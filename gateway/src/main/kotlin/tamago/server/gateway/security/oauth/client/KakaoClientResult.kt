package tamago.server.gateway.security.oauth.client

import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto

data class KakaoClientResult(
    val externalId: String,
    val email: String,
)

fun KakaoClientResult.toCommand(provider: OAuthProvider): LoginCommandDto =
    LoginCommandDto(
        externalId = this.externalId,
        email = this.email,
        provider = provider
    )