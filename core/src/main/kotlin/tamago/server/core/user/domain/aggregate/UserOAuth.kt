package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.domain.vo.UserOAuthId

class UserOAuth(
    val id: UserOAuthId? = null,
    val email: String,
    val provider: OAuthProvider,
    password: String? = null,
    externalId: String? = null,
    val userId: UserId? = null,
) {
    var password: String? = password
        private set

    var externalId: String? = externalId
        private set

    companion object {
        fun create(email: String, provider: OAuthProvider, credentials: String) =
            when (provider) {
                OAuthProvider.EMAIL -> UserOAuth(
                    email = email,
                    provider = provider,
                    password = credentials
                )
                else -> UserOAuth(
                    email = email,
                    provider = provider,
                    externalId = credentials
                )
            }
    }
}