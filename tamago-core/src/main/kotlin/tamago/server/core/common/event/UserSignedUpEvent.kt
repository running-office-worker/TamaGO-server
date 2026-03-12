package tamago.server.core.common.event

import tamago.server.core.common.vo.UserId

data class UserSignedUpEvent(
    val userId: UserId,
)
