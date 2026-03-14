package tamago.server.notification

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.IOException
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
                openCredentialsStream(firebaseProperties.credentialsPath).use {
                    GoogleCredentials.fromStream(it)
                }
            } catch (e: IOException) {
                throw IllegalStateException(
                    "Firebase credentials file not found: ${firebaseProperties.credentialsPath}",
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

    private fun openCredentialsStream(path: String): InputStream {
        val filePath = Paths.get(path)
        if (Files.exists(filePath)) {
            return Files.newInputStream(filePath)
        }
        return ClassPathResource(path).inputStream
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging = FirebaseMessaging.getInstance(firebaseApp)
}
