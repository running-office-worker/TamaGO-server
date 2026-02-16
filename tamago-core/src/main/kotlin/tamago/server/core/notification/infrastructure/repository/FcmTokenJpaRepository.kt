package tamago.server.core.notification.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.notification.infrastructure.entity.FcmTokenEntity

interface FcmTokenJpaRepository : JpaRepository<FcmTokenEntity, Long> {
    fun findAllByUserId(userId: Long): List<FcmTokenEntity>
}
