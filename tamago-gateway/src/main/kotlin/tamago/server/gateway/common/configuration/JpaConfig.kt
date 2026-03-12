package tamago.server.gateway.common.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["tamago.server.storage"])
@EnableJpaRepositories(basePackages = ["tamago.server.storage"])
class JpaConfig
