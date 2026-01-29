package tamago.server.gateway.common.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import tamago.server.gateway.common.properties.CorsProperties

@Configuration
@EnableConfigurationProperties(CorsProperties::class)
class CorsRegistryConfig(
    private val corsProperties: CorsProperties,
) {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration =
            CorsConfiguration().apply {
                allowedHeaders = listOf("*")
                allowedMethods = listOf("*")
                allowedOrigins = corsProperties.allowedOrigins
                allowCredentials = true
            }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
