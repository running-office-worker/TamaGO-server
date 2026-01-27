package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.domain.vo.UserAuthId

class UserAuth(
    val id: UserAuthId? = null,
    val email: String,
    val provider: AuthProvider,
    password: String? = null,
    externalId: String? = null,
    val userId: UserId? = null,
) {
    var password: String? = password
        private set

    var externalId: String? = externalId
        private set

    companion object {
        fun create(email: String, provider: AuthProvider, credentials: String) =
            when (provider) {
                AuthProvider.EMAIL -> UserAuth(
                    email = email,
                    provider = provider,
                    password = credentials
                )
                else -> UserAuth(
                    email = email,
                    provider = provider,
                    externalId = credentials
                )
            }
    }
}
