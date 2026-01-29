package tamago.server.oauth.configuration

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["tamago.server.oauth.client"])
internal class OAuthFeignConfig
