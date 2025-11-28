package tamago.server.gateway.response

import jakarta.servlet.http.HttpServletResponse
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import tamago.server.gateway.response.CustomResponse

@Aspect
@Component
class CustomResponseStatusAspect(
    private val response: HttpServletResponse,
) {
    @Around("within(tamago.server.gateway..*Controller)")
    @Throws(Throwable::class)
    fun handleResponseStatus(joinPoint: ProceedingJoinPoint): Any? {
        val result = joinPoint.proceed()

        if (result is CustomResponse<*>) {
            response.status = result.status.value()
        }

        return result
    }
}
