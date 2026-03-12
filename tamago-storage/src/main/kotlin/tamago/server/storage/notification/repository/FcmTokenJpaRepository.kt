package tamago.server.storage.notification.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.notification.entity.FcmTokenEntity

interface FcmTokenJpaRepository : JpaRepository<FcmTokenEntity, Long> {
    fun findAllByUserId(userId: Long): List<FcmTokenEntity>

    fun findAllByUserIdAndDeletedAtIsNull(userId: Long): List<FcmTokenEntity>

    fun findByUserIdAndDeviceIdAndDeletedAtIsNull(
        userId: Long,
        deviceId: String?,
    ): FcmTokenEntity?
}
