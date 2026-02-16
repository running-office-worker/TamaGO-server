package tamago.server.gateway.presentation.notification.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.notification.v1.request.FcmTokenRequest

@Tag(name = "Notification API", description = "푸시 알림 관련 API")
interface NotificationApi {
    @Operation(summary = "FCM 토큰 등록/갱신", description = "FCM 푸시 알림 토큰을 등록하거나 갱신합니다.")
    fun registerFcmToken(user: User, request: FcmTokenRequest): CustomResponse<Void>
}
