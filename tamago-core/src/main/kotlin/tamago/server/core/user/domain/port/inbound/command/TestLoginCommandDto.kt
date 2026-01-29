package tamago.server.core.user.domain.port.inbound.command

data class TestLoginCommandDto(
    val email: String,
    val password: String,
)