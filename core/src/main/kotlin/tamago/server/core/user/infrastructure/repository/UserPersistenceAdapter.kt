package tamago.server.core.user.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.outbound.UserPersistencePort
import tamago.server.core.user.infrastructure.mapper.UserMapper

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPersistencePort {
    override fun save(user: User): User =
        UserMapper.toDomain(userJpaRepository.save(UserMapper.toEntity(user)))!!

    override fun findByExternalId(provider: OAuthProvider, externalId: String): User? =
        UserMapper.toDomain(userJpaRepository.findByOauthsProviderAndOauthsExternalId(provider.name, externalId))

    override fun findByEmail(email: String): User? =
        UserMapper.toDomain(userJpaRepository.findByOauthsEmail(email))

    override fun existsByEmail(email: String): Boolean =
        userJpaRepository.existsByOauthsEmail(email)
}