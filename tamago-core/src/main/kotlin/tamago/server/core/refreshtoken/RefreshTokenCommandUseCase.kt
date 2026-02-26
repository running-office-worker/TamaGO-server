package tamago.server.core.refreshtoken

import tamago.server.core.common.vo.UserId
import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken

interface RefreshTokenCommandUseCase {
    fun saveOrUpdate(
        userId: UserId,
        token: String,
    ): RefreshToken

    fun rotate(
        userId: UserId,
        newToken: String,
    ): RefreshToken

    fun delete(userId: UserId)
}
