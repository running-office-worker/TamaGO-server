package tamago.server.weather.configuration

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["tamago.server.weather.client"])
internal class WeatherFeignConfig
