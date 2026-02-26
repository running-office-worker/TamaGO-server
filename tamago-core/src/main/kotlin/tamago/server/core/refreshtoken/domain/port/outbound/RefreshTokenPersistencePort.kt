package tamago.server.core.refreshtoken.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken

interface RefreshTokenPersistencePort {
    fun save(refreshToken: RefreshToken): RefreshToken

    fun findByUserId(userId: UserId): RefreshToken?

    fun deleteByUserId(userId: UserId)
}
