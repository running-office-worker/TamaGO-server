package tamago.server.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class TamaGoApplication

fun main(args: Array<String>) {
	runApplication<TamaGoApplication>(*args)
}
