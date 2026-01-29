package tamago.server.core.refreshtoken

import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.common.vo.UserId

interface RefreshTokenQueryUseCase {
    fun getByUserId(userId: UserId): RefreshToken
    fun findByUserId(userId: UserId): RefreshToken?
    fun validation(userId: UserId, token: String)
}
