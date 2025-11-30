package tamago.server.core.user.domain.aggregate

import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class User(
    val id: UserId? = null,
    val name: String,
    oauths: List<UserOAuth> = emptyList(),
    lastLoginAt: LocalDateTime? = null,
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
    withdrawAt: LocalDateTime? = null,
) {
    var lastLoginAt: LocalDateTime? = lastLoginAt
        private set

    companion object {
        fun create(
            name: String,
        ) = User(
            name = name
        )
    }
}