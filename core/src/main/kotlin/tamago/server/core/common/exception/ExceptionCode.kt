package tamago.server.core.common.exception

import org.springframework.http.HttpStatus

interface ExceptionCode {
    fun getStatus(): HttpStatus

    fun getCode(): String

    fun getMessage(): String
}
