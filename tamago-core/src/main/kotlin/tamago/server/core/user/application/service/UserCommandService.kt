package tamago.server.core.user.application.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.common.event.UserSignedUpEvent
import tamago.server.core.common.vo.UserId
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
@Transactional
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
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

    fun createSocialUser(command: LoginCommandDto) =
        userPersistencePort
            .save(User.create(command.email, command.provider, command.externalId))
            .also { publishUserSignedUpEvent(it.id!!) }

    override fun updateNickname(
        user: User,
        nickname: String,
    ) {
        user.updateNickname(nickname)
        userPersistencePort.save(user)
    }

    override fun recordLogin(user: User) {
        user.updateLastLogin()
        userPersistencePort.save(user)
    }

    override fun deleteUser(user: User) {
        user.delete()
        userPersistencePort.save(user)
        applicationEventPublisher.publishEvent(UserDeletedEvent(user.id!!))
    }

    fun addRunningDistance(
        user: User,
        distance: Double,
    ) {
        user.addRunningDistance(distance)
        userPersistencePort.save(user)
    }

    private fun publishUserSignedUpEvent(userId: UserId) {
        applicationEventPublisher.publishEvent(UserSignedUpEvent(userId))
    }
}
