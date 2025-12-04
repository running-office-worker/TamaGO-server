package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class User(
    val id: UserId? = null,
    oauths: List<UserOAuth> = emptyList(),
    lastLoginAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val withdrawAt: LocalDateTime? = null,
) {
    val oauths: List<UserOAuth> = oauths
        .sortedBy { it.provider }
        .toList()

    var lastLoginAt: LocalDateTime? = lastLoginAt
        private set

    fun updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }

    companion object {
        fun create(
            email: String,
            provider: OAuthProvider,
            externalId: String
        ): User {
            return User(
                oauths = listOf(
                    UserOAuth.create(
                        email = email,
                        provider = provider,
                        externalId = externalId
                    )
                ),
            )
        }
    }
}
