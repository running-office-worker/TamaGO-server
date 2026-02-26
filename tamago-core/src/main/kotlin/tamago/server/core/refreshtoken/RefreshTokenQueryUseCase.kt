package tamago.server.core.refreshtoken

import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken

interface RefreshTokenQueryUseCase {
    fun getByUserId(userId: UserId): RefreshToken

    fun findByUserId(userId: UserId): RefreshToken?

    fun validation(
        userId: UserId,
        token: String,
    )
}
