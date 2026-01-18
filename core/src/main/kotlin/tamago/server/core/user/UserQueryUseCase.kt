package tamago.server.core.user

import tamago.server.core.user.domain.aggregate.User

interface UserQueryUseCase {
    fun get(email: String): User
    fun exists(email: String): Boolean
}