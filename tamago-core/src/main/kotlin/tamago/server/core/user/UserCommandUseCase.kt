package tamago.server.core.user

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto

interface UserCommandUseCase {
    fun createUser(command: SignUpCommandDto)

    fun recordLogin(user: User)

    fun deleteUser(user: User)
}
