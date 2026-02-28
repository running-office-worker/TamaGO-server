package tamago.server.core.user.application.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.RefreshTokenCommandUseCase
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.event.UserSignedUpEvent
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
    private val refreshTokenCommandUseCase: RefreshTokenCommandUseCase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : UserCommandUseCase {
    override fun createUser(command: SignUpCommandDto) {
        val encodedPassword = passwordEncoder.encode(command.password)
        val user =
            User.create(
                command.email,
                AuthProvider.EMAIL,
                encodedPassword,
            )

        val savedUser = userPersistencePort.save(user)
        val userId = savedUser.id ?: throw UserSaveErrorException()

        publishUserSignedUpEvent(userId)
    }

    override fun socialLogin(command: LoginCommandDto): TokenQueryDto {
        val user =
            userPersistencePort.findByExternalId(command.provider, command.externalId)
                ?: run { createSocialUser(command) }

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

    override fun updateNickname(
        user: User,
        nickname: String,
    ) {
        user.updateNickname(nickname)
        userPersistencePort.save(user)
    }

    override fun updateGoalKilo(
        user: User,
        goalKilo: Int,
    ) {
        user.updateGoalKilo(goalKilo)
        userPersistencePort.save(user)
    }

    override fun recordLogin(user: User) {
        user.updateLastLogin()
        userPersistencePort.save(user)
    }

    fun addRunningDistance(
        user: User,
        distance: Double,
    ) {
        user.addRunningDistance(distance)
        userPersistencePort.save(user)
    }

    private fun createSocialUser(command: LoginCommandDto): User {
        val savedUser = userPersistencePort.save(User.create(command.email, command.provider, command.externalId))
        val userId = savedUser.id ?: throw UserSaveErrorException()

        publishUserSignedUpEvent(userId)
        return savedUser
    }

    private fun publishUserSignedUpEvent(userId: UserId) {
        applicationEventPublisher.publishEvent(UserSignedUpEvent(userId))
    }
}
