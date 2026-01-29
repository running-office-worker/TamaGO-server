package tamago.server.core.refreshtoken.domain.aggregate

import tamago.server.core.refreshtoken.domain.vo.RefreshTokenId
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class RefreshToken(
    val id: RefreshTokenId? = null,
    val userId: UserId,
    token: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var token: String? = token
        private set

    fun updateToken(token: String) {
        this.token = token
    }

    companion object {
        fun create(userId: UserId, token: String): RefreshToken {
            return RefreshToken(
                userId = userId,
                token = token,
            )
        }
    }
}
