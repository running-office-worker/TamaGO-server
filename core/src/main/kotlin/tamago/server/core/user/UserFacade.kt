package tamago.server.core.user

import org.springframework.stereotype.Component
import tamago.server.core.user.application.exception.EmailAlreadyExistsException
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto

@Component
class UserFacade(
    private val userCommandUseCase: UserCommandUseCase,
    private val userQueryUseCase: UserQueryUseCase,
) {
    fun signUp(command: SignUpCommandDto) = command
        .takeUnless { userQueryUseCase.exists(it.email) }
        ?.let(userCommandUseCase::createUser)
        ?: throw EmailAlreadyExistsException()

    fun socialLogin(command: LoginCommandDto): TokenQueryDto =
        userCommandUseCase.socialLogin(command)
}