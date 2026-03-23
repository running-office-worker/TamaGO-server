package tamago.server.core.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.refreshtoken.RefreshTokenCommandUseCase
import tamago.server.core.refreshtoken.RefreshTokenQueryUseCase
import tamago.server.core.user.application.exception.EmailAlreadyExistsException
import tamago.server.core.user.application.exception.InvalidCredentialsException
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.application.exception.UserWithdrawnException
import tamago.server.core.user.application.service.UserCommandService
import tamago.server.core.user.application.service.UserQueryService
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.command.TestLoginCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto

@Component
class UserFacade(
    private val userCommandService: UserCommandService,
    private val userQueryService: UserQueryService,
    private val refreshTokenCommandUseCase: RefreshTokenCommandUseCase,
    private val refreshTokenQueryUseCase: RefreshTokenQueryUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(command: SignUpCommandDto) =
        command
            .takeUnless { userQueryService.exists(it.email) }
            ?.let(userCommandService::createUser)
            ?: throw EmailAlreadyExistsException()

    fun socialLogin(command: LoginCommandDto): TokenQueryDto {
        command
            .takeUnless { userQueryService.existsDeletedByExternalId(it.provider, it.externalId) }
            ?: throw UserWithdrawnException()

        val user =
            userQueryService.findByExternalId(command.provider, command.externalId)
                ?: userCommandService.createSocialUser(command)

        val userId = user.id ?: throw UserSaveErrorException()

        val accessToken = jwtTokenProvider.generateAccessToken(userId, user.role.name)
        val refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role.name)

        refreshTokenCommandUseCase.saveOrUpdate(userId, refreshToken)

        return TokenQueryDto(
            userId = userId.value,
            accessToken = accessToken,
            refreshToken = refreshToken,
            isNewUser = !user.isOnboarded(),
        )
    }

    fun emailLogin(command: TestLoginCommandDto): TokenQueryDto {
        command
            .takeUnless { userQueryService.existsDeletedByEmail(it.email) }
            ?: throw UserWithdrawnException()

        val auth = userQueryService.getEmailAuth(command.email)

        require(passwordEncoder.matches(command.password, auth.password)) {
            throw InvalidCredentialsException()
        }

        val userId = auth.userId ?: throw InvalidCredentialsException()
        val user = userQueryService.get(userId)

        val accessToken = jwtTokenProvider.generateAccessToken(userId, user.role.name)
        val refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role.name)

        refreshTokenCommandUseCase.saveOrUpdate(userId, refreshToken)
        userCommandService.recordLogin(user)

        return TokenQueryDto(
            userId = userId.value,
            accessToken = accessToken,
            refreshToken = refreshToken,
            isNewUser = !user.isOnboarded(),
        )
    }

    fun reissueToken(refreshToken: String): TokenQueryDto {
        val userId = jwtTokenProvider.getUserId(refreshToken)
        val user = userQueryService.get(userId)

        refreshTokenQueryUseCase.validation(userId, refreshToken)

        val newAccessToken = jwtTokenProvider.generateAccessToken(userId, user.role.name)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role.name)

        refreshTokenCommandUseCase.rotate(userId, newRefreshToken)

        return TokenQueryDto(
            userId = userId.value,
            accessToken = newAccessToken,
            refreshToken = newRefreshToken,
            isNewUser = !user.isOnboarded(),
        )
    }
}
