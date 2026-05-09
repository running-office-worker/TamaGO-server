package tamago.server.weather.client.kma

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "weather.kma")
data class KmaWeatherProperties(
    val serviceKey: String,
) {
    override fun toString(): String = ""
}
