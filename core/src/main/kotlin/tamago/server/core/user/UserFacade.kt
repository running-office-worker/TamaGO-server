package tamago.server.core.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.user.application.exception.EmailAlreadyExistsException
import tamago.server.core.user.application.exception.InvalidCredentialsException
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.command.TestLoginCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto

@Component
class UserFacade(
    private val userCommandUseCase: UserCommandUseCase,
    private val userQueryUseCase: UserQueryUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(command: SignUpCommandDto) = command
        .takeUnless { userQueryUseCase.exists(it.email) }
        ?.let(userCommandUseCase::createUser)
        ?: throw EmailAlreadyExistsException()

    fun socialLogin(command: LoginCommandDto): TokenQueryDto =
        userCommandUseCase.socialLogin(command)

    fun emailLogin(command: TestLoginCommandDto): TokenQueryDto {
        val user = userQueryUseCase.getEmailOAuth(command.email)

        require(passwordEncoder.matches(command.password, user.password)) {
            throw InvalidCredentialsException()
        }

        val userId = user.userId ?: throw InvalidCredentialsException()

        return TokenQueryDto(
            userId = userId.value,
            accessToken = jwtTokenProvider.generateAccessToken(userId),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId),
            isNewUser = false,
        )
    }

}