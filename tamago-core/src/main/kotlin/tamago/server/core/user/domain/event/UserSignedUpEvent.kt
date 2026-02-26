package tamago.server.core.user.domain.event

import tamago.server.core.common.vo.UserId

data class UserSignedUpEvent(
    val userId: UserId,
)
