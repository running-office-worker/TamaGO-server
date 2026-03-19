package tamago.server.core.running.application.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class RunningExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    INVALID_RUNNING_DATA(HttpStatus.BAD_REQUEST, "RUNNING_4000", "러닝 데이터가 유효하지 않습니다."),
    RUNNING_NOT_FOUND(HttpStatus.NOT_FOUND, "RUNNING_4041", "러닝 기록을 찾을 수 없습니다."),
    RUNNING_PLAN_NOT_FOUND(HttpStatus.NOT_FOUND, "RUNNING_4040", "러닝 계획을 찾을 수 없습니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
