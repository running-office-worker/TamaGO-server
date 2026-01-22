package tamago.server.core.user.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.application.exception.InvalidCredentialsException
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserOAuth
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) : UserCommandUseCase {

    override fun createUser(command: SignUpCommandDto) {
        val encodedPassword = passwordEncoder.encode(command.password)
        val user = User.create(
            command.email,
            OAuthProvider.EMAIL,
            encodedPassword
        )

        userPersistencePort.save(user)
    }

    override fun socialLogin(command: LoginCommandDto): TokenQueryDto {
        val (userId, isNewUser) = userPersistencePort.findByExternalId(command.provider, command.externalId)?.id
            ?.let { existingUser -> existingUser to false }
            ?: run { createSocialUser(command) to true }

        return TokenQueryDto(
            userId = userId.value,
            accessToken = jwtTokenProvider.generateAccessToken(userId),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId),
            isNewUser = isNewUser,
        )
    }

    private fun createSocialUser(command: LoginCommandDto) =
        userPersistencePort.save(User.create(command.email, command.provider, command.externalId)).id
            ?: throw UserSaveErrorException()
}
