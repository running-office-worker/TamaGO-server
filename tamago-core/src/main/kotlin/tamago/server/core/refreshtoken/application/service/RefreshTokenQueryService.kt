package tamago.server.core.refreshtoken.application.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.refreshtoken.RefreshTokenQueryUseCase
import tamago.server.core.refreshtoken.application.exception.RefreshTokenMismatchException
import tamago.server.core.refreshtoken.application.exception.RefreshTokenNotFoundException
import tamago.server.core.refreshtoken.application.validator.RefreshTokenValidator
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.refreshtoken.domain.port.outbound.RefreshTokenPersistencePort
import tamago.server.core.common.vo.UserId

@Service
@Transactional(readOnly = true)
class RefreshTokenQueryService(
    private val refreshTokenPersistencePort: RefreshTokenPersistencePort,
    private val validator: RefreshTokenValidator
) : RefreshTokenQueryUseCase {

    override fun getByUserId(userId: UserId): RefreshToken {
        return refreshTokenPersistencePort.findByUserId(userId)
            ?: throw RefreshTokenNotFoundException()
    }

    override fun findByUserId(userId: UserId): RefreshToken? {
        return refreshTokenPersistencePort.findByUserId(userId)
    }

    override fun validation(userId: UserId, token: String) {
        validator.validate(token)
        validator.typeCheck(token)

        val storedToken = refreshTokenPersistencePort.findByUserId(userId)
            ?: throw RefreshTokenNotFoundException()

        if (storedToken.token != token) {
            throw RefreshTokenMismatchException()
        }
    }


}
