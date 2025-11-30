package tamago.server.core.user.domain.port.inbound.command

data class LoginCommandDto(
    val externalId: String,
    val email: String,
    val name: String,
)
