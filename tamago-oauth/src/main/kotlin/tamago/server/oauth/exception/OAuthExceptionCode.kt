package tamago.server.oauth.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class OAuthExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    AUTHENTICATION_ERROR(HttpStatus.BAD_REQUEST, "OAUTH_4000", "소셜 로그인 사용자를 검증하는데 실패했습니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
