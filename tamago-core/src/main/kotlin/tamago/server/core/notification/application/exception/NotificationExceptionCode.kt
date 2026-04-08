package tamago.server.core.notification.application.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class NotificationExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    FCM_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFICATION_4040", "등록된 FCM 토큰이 없습니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
