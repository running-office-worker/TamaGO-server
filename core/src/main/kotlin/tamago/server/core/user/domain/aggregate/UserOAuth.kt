package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.domain.vo.UserOAuthId

class UserOAuth(
    val id: UserOAuthId? = null,
    val email: String,
    val provider: OAuthProvider,
    val externalId: String,
    val userId: UserId,

    ) {
}