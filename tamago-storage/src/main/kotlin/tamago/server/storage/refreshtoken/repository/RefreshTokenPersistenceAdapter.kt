package tamago.server.storage.refreshtoken.repository

import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.port.outbound.RefreshTokenPersistencePort
import tamago.server.storage.refreshtoken.mapper.RefreshTokenMapper

@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
) : RefreshTokenPersistencePort {
    override fun save(refreshToken: RefreshToken): RefreshToken {
        val existingEntity = refreshTokenJpaRepository.findByUserId(refreshToken.userId.value)

        val entity =
            if (existingEntity != null) {
                existingEntity.token = refreshToken.token
                existingEntity
            } else {
                RefreshTokenMapper.toEntity(refreshToken)
            }

        val savedEntity = refreshTokenJpaRepository.save(entity)
        return RefreshTokenMapper.toDomain(savedEntity)!!
    }

    override fun findByUserId(userId: UserId): RefreshToken? =
        refreshTokenJpaRepository
            .findByUserId(userId.value)
            ?.let { RefreshTokenMapper.toDomain(it) }

    override fun deleteByUserId(userId: UserId) {
        refreshTokenJpaRepository.deleteByUserId(userId.value)
    }
}
