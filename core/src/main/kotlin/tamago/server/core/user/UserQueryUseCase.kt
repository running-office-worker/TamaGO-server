package tamago.server.core.user

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserOAuth

interface UserQueryUseCase {
    fun get(email: String): User
    fun getEmailOAuth(email: String): UserOAuth
    fun exists(email: String): Boolean
}