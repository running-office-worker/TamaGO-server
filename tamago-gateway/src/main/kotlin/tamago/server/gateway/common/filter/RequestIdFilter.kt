package tamago.server.gateway.common.filter

import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.UUID
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class RequestIdFilter : HttpFilter() {

    companion object {
        private const val REQUEST_ID_KEY = "requestId"
        private const val HEADER_REQUEST_ID = "X-Request-Id"
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val requestId = UUID.randomUUID().toString()

        try {
            MDC.put(REQUEST_ID_KEY, requestId)
            response.setHeader(HEADER_REQUEST_ID, requestId)

            chain.doFilter(request, response)
        } finally {
            MDC.remove(REQUEST_ID_KEY)
        }
    }
}
