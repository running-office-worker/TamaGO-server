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
import tamago.server.core.user.domain.vo.UserId

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
        val oauth = userQueryUseCase.getEmailOAuth(command.email)

        require(passwordEncoder.matches(command.password, oauth.password)) {
            throw InvalidCredentialsException()
        }

        val userId = oauth.userId ?: throw InvalidCredentialsException()
        val user = userQueryUseCase.get(userId)

        val response = TokenQueryDto(
            userId = userId.value,
            accessToken = jwtTokenProvider.generateAccessToken(userId, user.role),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role),
            isNewUser = false,
        )
        userCommandUseCase.recordLogin(user)

        return response
    }

    fun giveNickname(userId: UserId, nickname: String) =
        userQueryUseCase.get(userId)
            .let { userCommandUseCase.updateNickname(it, nickname) }

}