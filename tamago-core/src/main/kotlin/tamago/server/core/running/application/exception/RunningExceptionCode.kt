package tamago.server.core.running.application.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class RunningExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    INVALID_RUNNING_DATA(HttpStatus.BAD_REQUEST, "RUNNING_4000", "러닝 데이터가 유효하지 않습니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
