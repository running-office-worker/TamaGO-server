package tamago.server.core.user

import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto

interface UserLoginUseCase {
    fun login(command: LoginCommandDto): TokenQueryDto

}