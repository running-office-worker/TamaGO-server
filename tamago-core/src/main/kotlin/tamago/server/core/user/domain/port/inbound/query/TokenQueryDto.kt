package tamago.server.core.user.domain.port.inbound.query

data class TokenQueryDto(
    val userId: Long,
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,
)
