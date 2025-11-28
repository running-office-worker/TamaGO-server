package tamago.server.gateway.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["tamago.server.core"])
@EnableJpaRepositories(basePackages = ["tamago.server.core"])
class JpaConfig
