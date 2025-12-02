package tamago.server.gateway.security.oauth.client

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("apple")
data class AppleProperties(
    val bundleId: String,
)
