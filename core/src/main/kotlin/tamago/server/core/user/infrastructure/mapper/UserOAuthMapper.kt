package tamago.server.core.user.infrastructure.mapper

import tamago.server.core.user.domain.aggregate.UserOAuth
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.user.domain.vo.UserOAuthId
import tamago.server.core.user.infrastructure.entity.UserOAuthEntity

object UserOAuthMapper {
    fun toDomain(oauths: List<UserOAuthEntity>): List<UserOAuth> {
        return oauths.map {
            UserOAuth(
                id = UserOAuthId(it.id ?: 0L),
                email = it.email,
                provider = OAuthProvider.valueOf(it.provider),
                externalId = it.externalId,
                userId = UserId(it.user.id ?: 0L),
            )
        }
    }
}