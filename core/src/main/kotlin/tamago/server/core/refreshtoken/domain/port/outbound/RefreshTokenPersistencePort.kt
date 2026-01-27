package tamago.server.core.refreshtoken.domain.port.outbound

import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.user.domain.vo.UserId

interface RefreshTokenPersistencePort {
    fun save(refreshToken: RefreshToken): RefreshToken
    fun findByUserId(userId: UserId): RefreshToken?
    fun deleteByUserId(userId: UserId)
}
