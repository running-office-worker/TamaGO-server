package tamago.server.gateway.security.oauth.client

import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto

data class KakaoClientResult(
    val externalId: String,
    val email: String,
    val name: String,
)

fun KakaoClientResult.toCommand(): LoginCommandDto =
    LoginCommandDto(
        externalId = this.externalId,
        email = this.email,
        name = this.name,
    )