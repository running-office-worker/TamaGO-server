package tamago.server.weather.client.kma

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "kma-weather-api", url = "\${weather.kma.base-url}")
internal interface KmaWeatherApi
