package tamago.server.core

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("ci")
@EnabledIfEnvironmentVariable(named = "CI", matches = "true")
class SchemaValidationTest {

    @Test
    fun contextLoads() {
        // 컨텍스트가 정상적으로 로드되면 JPA 엔티티와 DB 스키마가 일치함을 의미합니다.
    }
}
