package tamago.server.core.user.application.service

import org.springframework.stereotype.Service
import tamago.server.core.user.UserQueryUseCase
import tamago.server.core.user.application.exception.EmailAlreadyExistsException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.port.outbound.UserPersistencePort

@Service
class UserQueryService(
    private val userPersistencePort: UserPersistencePort,
) : UserQueryUseCase {
    override fun get(email: String): User =
        userPersistencePort.findByEmail(email)
            ?: throw EmailAlreadyExistsException()

    override fun exists(email: String): Boolean =
        userPersistencePort.existsByEmail(email)

}