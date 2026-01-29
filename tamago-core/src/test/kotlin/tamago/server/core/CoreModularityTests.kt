package tamago.server.core

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class CoreModularityTests {

    @Test
    fun verifyModuleStructure() {
        val modules = ApplicationModules.of("tamago.server.core")
        modules.verify()
    }
}
