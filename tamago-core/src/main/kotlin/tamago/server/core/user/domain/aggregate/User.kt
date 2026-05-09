package tamago.server.core.user.domain.aggregate

import tamago.server.core.common.vo.UserId
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.enum.UserRole
import java.time.LocalDateTime

class User(
    val id: UserId? = null,
    nickname: String? = null,
    val role: UserRole = UserRole.USER,
    auths: List<UserAuth> = emptyList(),
    runningData: UserRunningData = UserRunningData(),
    weight: Double = 70.0,
    lastLoginAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    deletedAt: LocalDateTime? = null,
) {
    var deletedAt: LocalDateTime? = deletedAt
        private set

    var nickname: String? = nickname
        private set

    val auths: List<UserAuth> =
        auths
            .sortedBy { it.provider }
            .toList()

    var runningData: UserRunningData = runningData
        private set

    var weight: Double = weight
        private set

    var lastLoginAt: LocalDateTime? = lastLoginAt
        private set

    fun updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now()
    }

    fun addRunningDistance(distance: Double) {
        this.runningData = runningData.addKilo(distance)
    }

    fun delete() {
        if (this.deletedAt != null) return
        this.deletedAt = LocalDateTime.now()
    }

    fun restore() {
        this.deletedAt = null
    }

    companion object {
        fun create(
            email: String,
            provider: AuthProvider,
            credentials: String,
        ): User =
            User(
                auths =
                    listOf(
                        UserAuth.create(
                            email = email,
                            provider = provider,
                            credentials = credentials,
                        ),
                    ),
            )
    }
}
