package tamago.server.core.refreshtoken.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.port.outbound.RefreshTokenPersistencePort
import tamago.server.core.refreshtoken.infrastructure.mapper.RefreshTokenMapper
import tamago.server.core.common.vo.UserId

@Repository
class RefreshTokenPersistenceAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
) : RefreshTokenPersistencePort {

    override fun save(refreshToken: RefreshToken): RefreshToken {
        val existingEntity = refreshTokenJpaRepository.findByUserId(refreshToken.userId.value)

        val entity = if (existingEntity != null) {
            existingEntity.token = refreshToken.token
            existingEntity
        } else {
            RefreshTokenMapper.toEntity(refreshToken)
        }

        val savedEntity = refreshTokenJpaRepository.save(entity)
        return RefreshTokenMapper.toDomain(savedEntity)!!
    }

    override fun findByUserId(userId: UserId): RefreshToken? {
        return refreshTokenJpaRepository.findByUserId(userId.value)
            ?.let { RefreshTokenMapper.toDomain(it) }
    }

    override fun deleteByUserId(userId: UserId) {
        refreshTokenJpaRepository.deleteByUserId(userId.value)
    }
}
