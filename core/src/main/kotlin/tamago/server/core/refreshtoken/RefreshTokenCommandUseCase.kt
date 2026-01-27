package tamago.server.core.refreshtoken

import tamago.server.core.refreshtoken.domain.aggregate.RefreshToken
import tamago.server.core.user.domain.vo.UserId

interface RefreshTokenCommandUseCase {
    fun saveOrUpdate(userId: UserId, token: String): RefreshToken
    fun rotate(userId: UserId, newToken: String): RefreshToken
    fun delete(userId: UserId)
}
