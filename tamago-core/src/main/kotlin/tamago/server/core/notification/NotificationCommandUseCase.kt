package tamago.server.core.notification

import tamago.server.core.common.vo.UserId

interface NotificationCommandUseCase {
    fun sendLetterArrivalNotification(
        tokens: List<String>,
        title: String,
        body: String,
    )

    fun deleteFcmTokensByUserId(userId: UserId)
}
