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
        return fcmTokenJpaRepository.findAllByUserId(userId.value)
            .mapNotNull { FcmTokenMapper.toDomain(it) }
    }
}
