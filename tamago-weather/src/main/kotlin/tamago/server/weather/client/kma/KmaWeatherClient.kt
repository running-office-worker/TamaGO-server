package tamago.server.weather.client.kma

import org.springframework.stereotype.Component

@Component
class KmaWeatherClient internal constructor(
    private val kmaWeatherApi: KmaWeatherApi,
)
