package tamago.server.oauth.client.apple

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("apple")
data class AppleProperties(
    val bundleId: String,
)
