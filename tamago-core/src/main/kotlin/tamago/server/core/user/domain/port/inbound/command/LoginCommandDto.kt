package tamago.server.core.user.domain.port.inbound.command

import tamago.server.core.user.domain.enum.AuthProvider

data class LoginCommandDto(
    val externalId: String,
    val email: String,
    val provider: AuthProvider
)
