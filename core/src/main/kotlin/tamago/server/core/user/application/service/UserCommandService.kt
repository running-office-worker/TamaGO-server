package tamago.server.core.user.application.service

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.domain.aggregate.User
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
        val (user, isNewUser) = userPersistencePort.findByExternalId(command.provider, command.externalId)
            ?.let { it to false }
            ?: run { createSocialUser(command) to true }

        val userId = user.id ?: throw UserSaveErrorException()

        return TokenQueryDto(
            userId = userId.value,
            accessToken = jwtTokenProvider.generateAccessToken(userId, user.role),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId, user.role),
            isNewUser = isNewUser,
        )
    }

    override fun updateNickname(user: User, nickname: String) {
        user.updateNickname(nickname)
        userPersistencePort.save(user)
    }

    override fun recordLogin(user: User) {
        user.updateLastLogin()
        userPersistencePort.save(user)
    }

    private fun createSocialUser(command: LoginCommandDto): User =
        userPersistencePort.save(User.create(command.email, command.provider, command.externalId))
}
