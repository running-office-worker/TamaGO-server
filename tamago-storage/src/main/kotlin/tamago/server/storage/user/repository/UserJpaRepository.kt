package tamago.server.storage.user.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.user.entity.UserEntity

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByAuthsProviderAndAuthsExternalIdAndDeletedAtIsNull(
        provider: String,
        externalId: String,
    ): UserEntity?

    fun findByAuthsProviderAndAuthsExternalId(
        provider: String,
        externalId: String,
    ): UserEntity?

    fun findByAuthsEmailAndDeletedAtIsNull(email: String): UserEntity?

    fun findByAuthsEmail(email: String): UserEntity?

    fun existsByAuthsEmailAndDeletedAtIsNull(email: String): Boolean

    fun existsByAuthsEmail(email: String): Boolean

    fun existsByAuthsProviderAndAuthsExternalIdAndDeletedAtIsNotNull(
        provider: String,
        externalId: String,
    ): Boolean

    fun existsByAuthsEmailAndDeletedAtIsNotNull(email: String): Boolean
}
