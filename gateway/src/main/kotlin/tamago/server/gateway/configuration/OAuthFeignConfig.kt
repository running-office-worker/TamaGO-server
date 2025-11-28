package tamago.server.gateway.configuration

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["tamago.server.gateway.security.oauth.client"])
internal class OAuthFeignConfig
