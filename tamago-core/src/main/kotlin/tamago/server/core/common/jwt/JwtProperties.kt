package tamago.server.core.common.jwt

import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    @field:NotNull
    val accessTokenExpiryMs: Long,
    @field:NotNull
    val refreshTokenExpiryMs: Long,
)
