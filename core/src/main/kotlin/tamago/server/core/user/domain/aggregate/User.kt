package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.domain.vo.UserOAuthId
import java.time.LocalDateTime

class User(
    val id: UserId? = null,
    val name: String,
    lastLoginProviderId: UserOAuthId? = null,
    lastLoginAt: LocalDateTime? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
    withdrawAt: LocalDateTime? = null,
) {

}