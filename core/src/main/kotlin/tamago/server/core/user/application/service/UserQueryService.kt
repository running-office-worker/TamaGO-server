package tamago.server.core.user.application.service

import org.springframework.stereotype.Service
import tamago.server.core.user.UserQueryUseCase
import tamago.server.core.user.application.exception.InvalidCredentialsException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.aggregate.UserOAuth
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
class UserQueryService(
    private val userPersistencePort: UserPersistencePort,
) : UserQueryUseCase {
    override fun get(email: String): User =
        userPersistencePort.findByEmail(email)
            ?: throw InvalidCredentialsException()

    override fun getEmailOAuth(email: String): UserOAuth {
        val user = userPersistencePort.findByEmail(email)
            ?: throw InvalidCredentialsException()

        return user.oauths.find { it.provider == OAuthProvider.EMAIL }
            ?: throw InvalidCredentialsException()
    }

    override fun exists(email: String): Boolean =
        userPersistencePort.existsByEmail(email)
}