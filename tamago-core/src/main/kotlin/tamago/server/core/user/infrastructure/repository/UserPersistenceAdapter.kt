package tamago.server.core.user.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.outbound.UserPersistencePort
import tamago.server.core.common.vo.UserId
import tamago.server.core.user.infrastructure.mapper.UserMapper

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserPersistencePort {
    override fun save(user: User): User =
        UserMapper.toDomain(userJpaRepository.save(UserMapper.toEntity(user)))!!

    override fun findById(id: UserId): User? =
        UserMapper.toDomain(userJpaRepository.findById(id.value).orElse(null))

    override fun findByExternalId(provider: AuthProvider, externalId: String): User? =
        UserMapper.toDomain(userJpaRepository.findByAuthsProviderAndAuthsExternalId(provider.name, externalId))

    override fun findByEmail(email: String): User? =
        UserMapper.toDomain(userJpaRepository.findByAuthsEmail(email))

    override fun existsByEmail(email: String): Boolean =
        userJpaRepository.existsByAuthsEmail(email)
}
