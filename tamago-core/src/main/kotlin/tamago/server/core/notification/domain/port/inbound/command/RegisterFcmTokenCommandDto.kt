package tamago.server.core.notification.domain.port.inbound.command

import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.enum.DeviceType

data class RegisterFcmTokenCommandDto(
    val userId: UserId,
    val deviceId: String? = null,
    val token: String,
    val deviceType: DeviceType? = null,
)
