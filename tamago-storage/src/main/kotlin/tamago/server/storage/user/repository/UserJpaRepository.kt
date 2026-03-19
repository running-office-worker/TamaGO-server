package tamago.server.storage.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.user.entity.UserEntity

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByAuthsProviderAndAuthsExternalIdAndDeletedAtIsNull(
        provider: String,
        externalId: String,
    ): UserEntity?

    fun findByAuthsEmail(email: String): UserEntity?

    fun existsByAuthsEmail(email: String): Boolean
}
