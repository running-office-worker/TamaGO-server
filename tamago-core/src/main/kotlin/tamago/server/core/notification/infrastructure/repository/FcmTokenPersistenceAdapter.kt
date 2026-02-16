package tamago.server.core.notification.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.notification.domain.aggregate.FcmToken
import tamago.server.core.notification.domain.port.outbound.FcmTokenPersistencePort
import tamago.server.core.notification.infrastructure.mapper.FcmTokenMapper

@Repository
class FcmTokenPersistenceAdapter(
    private val fcmTokenJpaRepository: FcmTokenJpaRepository,
) : FcmTokenPersistencePort {

    override fun findAllByUserId(userId: UserId): List<FcmToken> {
        return fcmTokenJpaRepository.findAllByUserIdAndDeletedAtIsNull(userId.value)
            .mapNotNull { FcmTokenMapper.toDomain(it) }
    }

    override fun findByUserId(userId: UserId): FcmToken? {
        return fcmTokenJpaRepository.findByUserIdAndDeletedAtIsNull(userId.value)
            ?.let { FcmTokenMapper.toDomain(it) }
    }

    override fun save(fcmToken: FcmToken): FcmToken {
        val entity = FcmTokenMapper.toEntity(fcmToken)
        val saved = fcmTokenJpaRepository.save(entity)
        return FcmTokenMapper.toDomain(saved)!!
    }
}
