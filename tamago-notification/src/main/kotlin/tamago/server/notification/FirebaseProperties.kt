package tamago.server.notification

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "fcm")
data class FirebaseProperties(
    val credentialsPath: String,
)
