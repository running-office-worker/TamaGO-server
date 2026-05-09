package tamago.server.weather

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "weather.kma")
data class KmaWeatherProperties(
    val baseUrl: String,
    val serviceKey: String,
) {
    override fun toString(): String = ""
}
