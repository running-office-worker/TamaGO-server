package tamago.server.core.refreshtoken.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.refreshtoken.infrastructure.entity.RefreshTokenEntity

interface RefreshTokenJpaRepository : JpaRepository<RefreshTokenEntity, Long> {
    fun findByUserId(userId: Long): RefreshTokenEntity?
    fun deleteByUserId(userId: Long)
}
