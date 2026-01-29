package tamago.server.core.user.domain.port.inbound.command

data class SignUpCommandDto(
    val email: String,
    val password: String,
)
