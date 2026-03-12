package tamago.server.storage.refreshtoken.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.refreshtoken.entity.RefreshTokenEntity

interface RefreshTokenJpaRepository : JpaRepository<RefreshTokenEntity, Long> {
    fun findByUserId(userId: Long): RefreshTokenEntity?

    fun deleteByUserId(userId: Long)
}
