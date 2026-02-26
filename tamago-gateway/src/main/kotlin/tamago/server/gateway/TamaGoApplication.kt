package tamago.server.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan(basePackages = ["tamago.server"])
@SpringBootApplication(scanBasePackages = ["tamago.server"])
class TamaGoApplication

fun main(args: Array<String>) {
    runApplication<TamaGoApplication>(*args)
}
