package tamago.server.core.letter.application.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class LetterExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    RECEIVED_LETTER_NOT_FOUND(HttpStatus.NOT_FOUND, "LETTER_4040", "받은 편지를 찾을 수 없습니다."),
    LETTER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "LETTER_4030", "해당 편지에 접근 권한이 없습니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
