package tamago.server.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TamaGoApplication

fun main(args: Array<String>) {
	runApplication<TamaGoApplication>(*args)
}
