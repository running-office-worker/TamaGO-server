package tamago.server.core.user.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.user.UserQueryUseCase
import tamago.server.core.user.application.exception.InvalidCredentialsException
import tamago.server.core.user.application.exception.UserNotFoundException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserAuth
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
class UserQueryService(
    private val userPersistencePort: UserPersistencePort,
) : UserQueryUseCase {
    override fun get(userId: UserId): User =
        userPersistencePort.findById(userId)
            ?: throw UserNotFoundException()

    override fun get(email: String): User =
        userPersistencePort.findByEmail(email)
            ?: throw InvalidCredentialsException()

    override fun getEmailAuth(email: String): UserAuth {
        val user =
            userPersistencePort.findByEmail(email)
                ?: throw InvalidCredentialsException()

        return user.auths.find { it.provider == AuthProvider.EMAIL }
            ?: throw InvalidCredentialsException()
    }

    fun findByExternalId(
        provider: AuthProvider,
        externalId: String,
    ): User? = userPersistencePort.findByExternalId(provider, externalId)

    override fun exists(email: String): Boolean = userPersistencePort.existsByEmail(email)
}
