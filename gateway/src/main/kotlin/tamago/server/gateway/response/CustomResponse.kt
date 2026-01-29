package tamago.server.gateway.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode
import tamago.server.core.common.exception.GlobalExceptionCode

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = ["requestId", "status", "message", "code", "data"])
data class CustomResponse<T>(

    @field:Schema(
        description = "요청 식별자", example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = REQUIRED
    ) val requestId: String? = MDC.get("requestId"),

    @field:Schema(
        description = "HTTP 상태 코드", example = "OK", requiredMode = REQUIRED
    ) val status: HttpStatus,

    @field:Schema(
        description = "응답 메시지", example = "요청에 성공했습니다.", requiredMode = REQUIRED
    ) val message: String,

    @field:Schema(
        description = "응답 코드", example = "GLOBAL_2000", requiredMode = REQUIRED
    ) val code: String,

    val data: T? = null
) {

    companion object {

        fun <T> ok(): CustomResponse<T> = ok(null)

        fun <T> ok(data: T?): CustomResponse<T> = ok(data, GlobalExceptionCode.SUCCESS)

        fun <T> ok(
            data: T?,
            globalExceptionCode: GlobalExceptionCode
        ): CustomResponse<T> = CustomResponse(
            status = globalExceptionCode.status,
            message = globalExceptionCode.message,
            code = globalExceptionCode.code,
            data = data
        )

        fun <T> created(): CustomResponse<T> = CustomResponse(
            status = GlobalExceptionCode.CREATED.status,
            message = GlobalExceptionCode.CREATED.message,
            code = GlobalExceptionCode.CREATED.code,
        )

        fun noContent(): CustomResponse<Void> = CustomResponse(
            status = GlobalExceptionCode.NO_CONTENT.status,
            message = GlobalExceptionCode.NO_CONTENT.message,
            code = GlobalExceptionCode.NO_CONTENT.code,
            data = null
        )

        fun error(exceptionCode: ExceptionCode): CustomResponse<Void> = error(exceptionCode, exceptionCode.getMessage())

        fun error(
            exceptionCode: ExceptionCode, message: String
        ): CustomResponse<Void> = CustomResponse(
            status = exceptionCode.getStatus(), message = message, code = exceptionCode.getCode(), data = null
        )
    }
}
