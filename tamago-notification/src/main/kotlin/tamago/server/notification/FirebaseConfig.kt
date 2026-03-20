package tamago.server.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
@EnableConfigurationProperties(FirebaseProperties::class)
class FirebaseConfig(
    private val firebaseProperties: FirebaseProperties,
) {
    @Bean
    fun firebaseApp(): FirebaseApp {
        if (FirebaseApp.getApps().isNotEmpty()) {
            return FirebaseApp.getInstance()
        }

        val credentials =
            try {
                openCredentialsStream().use {
                    GoogleCredentials.fromStream(it)
                }
            } catch (e: Exception) {
                throw IllegalStateException(
                    "Failed to load Firebase credentials - ${e.message}",
                    e,
                )
            }

        val options =
            FirebaseOptions
                .builder()
                .setCredentials(credentials)
                .build()

        return FirebaseApp.initializeApp(options)
    }

    private fun openCredentialsStream(): InputStream {
        val json = firebaseProperties.credentialsJson.trim().removeSurrounding("\"")
        if (json.isNotBlank()) {
            val unescaped = json.replace("\\n", "\n").replace("\\\"", "\"")
            return ByteArrayInputStream(unescaped.toByteArray())
        }

        val path = firebaseProperties.credentialsPath
        return try {
            Files.newInputStream(Paths.get(path))
        } catch (e: Exception) {
            ClassPathResource(path).inputStream
        }
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)
}
