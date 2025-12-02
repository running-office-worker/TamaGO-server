package tamago.server.core.user.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.user.infrastructure.entity.UserEntity

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByOauthsProviderAndOauthsExternalId(provider: String, externalId: String): UserEntity?
}