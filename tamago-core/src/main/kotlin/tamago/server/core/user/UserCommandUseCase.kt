package tamago.server.core.user

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto

interface UserCommandUseCase {
    fun socialLogin(command: LoginCommandDto): TokenQueryDto

    fun createUser(command: SignUpCommandDto)

    fun updateNickname(
        user: User,
        nickname: String,
    )

    fun updateGoalKilo(
        user: User,
        goalKilo: Int,
    )

    fun recordLogin(user: User)
}
