package tamago.server.storage.user.mapper

import tamago.server.core.common.vo.UserId
import tamago.server.core.user.domain.aggregate.UserAuth
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.vo.UserAuthId
import tamago.server.storage.user.entity.UserAuthEntity

object UserAuthMapper {
    fun toDomain(auths: List<UserAuthEntity>): List<UserAuth> =
        auths.map {
            UserAuth(
                id = UserAuthId(it.id ?: 0L),
                email = checkNotNull(it.email),
                provider = AuthProvider.valueOf(checkNotNull(it.provider)),
                password = it.password,
                externalId = it.externalId,
                userId = UserId(it.user?.id ?: 0L),
            )
        }

    fun toEntity(auth: UserAuth): UserAuthEntity =
        UserAuthEntity(
            id = auth.id?.value,
            email = auth.email,
            provider = auth.provider.name,
            password = auth.password,
            externalId = auth.externalId,
        )
}
