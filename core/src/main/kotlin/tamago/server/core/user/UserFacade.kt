package tamago.server.core.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.refreshtoken.RefreshTokenCommandUseCase
import tamago.server.core.refreshtoken.RefreshTokenQueryUseCase
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
    private val refreshTokenCommandUseCase: RefreshTokenCommandUseCase,
    private val refreshTokenQueryUseCase: RefreshTokenQueryUseCase,
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
        val auth = userQueryUseCase.getEmailAuth(command.email)

        require(passwordEncoder.matches(command.password, auth.password)) {
            throw InvalidCredentialsException()
        }

        val userId = auth.userId ?: throw InvalidCredentialsException()
        val user = userQueryUseCase.get(userId)

        val accessToken = jwtTokenProvider.generateAccessToken(userId, user.role)
        val refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role)

        refreshTokenCommandUseCase.saveOrUpdate(userId, refreshToken)
        userCommandUseCase.recordLogin(user)

        return TokenQueryDto(
            userId = userId.value,
            accessToken = accessToken,
            refreshToken = refreshToken,
            isNewUser = false,
        )
    }

    fun giveNickname(userId: UserId, nickname: String) =
        userQueryUseCase.get(userId)
            .let { userCommandUseCase.updateNickname(it, nickname) }

    fun reissueToken(refreshToken: String): TokenQueryDto {
        val userId = jwtTokenProvider.getUserId(refreshToken)
        val role = jwtTokenProvider.getUserRole(refreshToken)

        refreshTokenQueryUseCase.validation(userId, refreshToken)

        val newAccessToken = jwtTokenProvider.generateAccessToken(userId, role)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(userId, role)

        refreshTokenCommandUseCase.rotate(userId, newRefreshToken)

        return TokenQueryDto(
            userId = userId.value,
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            isNewUser = false,
        )
    }
}
