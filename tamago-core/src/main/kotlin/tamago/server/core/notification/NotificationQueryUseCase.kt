package tamago.server.core.notification

import tamago.server.core.common.vo.UserId

interface NotificationQueryUseCase {
    fun getFcmTokensByUserId(userId: UserId): List<String>
}
