package tamago.server.core.user.domain.port.outbound

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.vo.UserId

interface UserPersistencePort {
    fun save(user: User): User
    fun findById(id: UserId): User?
    fun findByExternalId(provider: AuthProvider, externalId: String): User?
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
}
