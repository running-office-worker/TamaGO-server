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
                email = checkNotNull(it.email),
                provider = OAuthProvider.valueOf(checkNotNull(it.provider)),
                password = it.password,
                externalId = it.externalId,
                userId = UserId(it.user?.id ?: 0L),
            )
        }
    }

    fun toEntity(oauth: UserOAuth): UserOAuthEntity {
        return UserOAuthEntity(
            id = oauth.id?.value,
            email = oauth.email,
            provider = oauth.provider.name,
            password = oauth.password,
            externalId = oauth.externalId,
        )
    }
}