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
    goalKilo: Int? = null,
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

    var goalKilo: Int? = goalKilo
        private set

    var lastLoginAt: LocalDateTime? = lastLoginAt
        private set

    fun updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateGoalKilo(goalKilo: Int) {
        this.goalKilo = goalKilo
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
