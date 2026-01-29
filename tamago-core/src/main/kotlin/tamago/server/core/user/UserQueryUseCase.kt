package tamago.server.core.user

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserAuth
import tamago.server.core.user.domain.vo.UserId

interface UserQueryUseCase {
    fun get(userId: UserId): User
    fun get(email: String): User
    fun getEmailAuth(email: String): UserAuth
    fun exists(email: String): Boolean
}
