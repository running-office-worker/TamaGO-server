package tamago.server.core.user

import tamago.server.core.common.vo.UserId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserAuth

interface UserQueryUseCase {
    fun get(userId: UserId): User

    fun get(email: String): User

    fun getEmailAuth(email: String): UserAuth

    fun exists(email: String): Boolean
}
