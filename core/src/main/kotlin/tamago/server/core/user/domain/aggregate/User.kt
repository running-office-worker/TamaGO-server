package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.enum.UserRole
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class User(
    val id: UserId? = null,
    nickname: String? = null,
    val role: UserRole = UserRole.USER,
    auths: List<UserAuth> = emptyList(),
    lastLoginAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var nickname: String? = nickname
        private set

    val auths: List<UserAuth> = auths
        .sortedBy { it.provider }
        .toList()

    var lastLoginAt: LocalDateTime? = lastLoginAt
        private set

    fun updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    companion object {
        fun create(
            email: String,
            provider: AuthProvider,
            credentials: String
        ): User {
            return User(
                auths = listOf(
                    UserAuth.create(
                        email = email,
                        provider = provider,
                        credentials = credentials
                    )
                ),
            )
        }
    }
}
