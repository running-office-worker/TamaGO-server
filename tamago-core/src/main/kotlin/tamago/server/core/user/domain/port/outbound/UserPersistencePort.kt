package tamago.server.core.user.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider

interface UserPersistencePort {
    fun save(user: User): User

    fun findById(id: UserId): User?

    fun findByExternalId(
        provider: AuthProvider,
        externalId: String,
    ): User?

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun existsDeletedByExternalId(
        provider: AuthProvider,
        externalId: String,
    ): Boolean
}
