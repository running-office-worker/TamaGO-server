package tamago.server.core.notification

import tamago.server.core.common.vo.UserId

interface NotificationCommandUseCase {
    fun createNotification(
        userId: UserId,
        title: String,
        content: String,
    )
}
