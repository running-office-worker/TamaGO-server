package tamago.server.core.refreshtoken.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.RefreshTokenCommandUseCase
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.port.outbound.RefreshTokenPersistencePort

@Service
@Transactional
class RefreshTokenCommandService(
    private val refreshTokenPersistencePort: RefreshTokenPersistencePort,
) : RefreshTokenCommandUseCase {
    override fun saveOrUpdate(
        userId: UserId,
        token: String,
    ): RefreshToken {
        val refreshToken =
            refreshTokenPersistencePort
                .findByUserId(userId)
                ?.apply { updateToken(token) }
                ?: RefreshToken.create(userId, token)

        return refreshTokenPersistencePort.save(refreshToken)
    }

    override fun rotate(
        userId: UserId,
        newToken: String,
    ): RefreshToken = saveOrUpdate(userId, newToken)

    override fun delete(userId: UserId) {
        refreshTokenPersistencePort.deleteByUserId(userId)
    }
}
