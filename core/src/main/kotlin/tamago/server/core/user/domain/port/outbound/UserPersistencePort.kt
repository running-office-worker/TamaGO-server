package tamago.server.core.user.domain.port.outbound

import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.OAuthProvider

interface UserPersistencePort {
    fun save(user: User): User
    fun findByExternalId(provider: OAuthProvider, externalId: String): User?
}