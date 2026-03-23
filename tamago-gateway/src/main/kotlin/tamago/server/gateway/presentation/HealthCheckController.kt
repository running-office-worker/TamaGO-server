package tamago.server.gateway.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health Check API", description = "서버 상태 확인 API")
@RestController
@RequestMapping("/api/health")
class HealthCheckController {
    @Operation(summary = "500 에러 발생", description = "Sentry 연동 테스트용 500 에러를 발생시킵니다.")
    @GetMapping("/error")
    fun triggerError(): Nothing = throw RuntimeException("Sentry 테스트용 500 에러")
}
