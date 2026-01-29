package tamago.server.core.usermonster.domain.aggregate

import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.user.domain.vo.UserId
import tamago.server.core.usermonster.domain.vo.UserMonsterId
import java.time.LocalDateTime

class UserMonster(
    val id: UserMonsterId? = null,
    val monsterId: MonsterId,
    val userId: UserId,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(monsterId: MonsterId, userId: UserId): UserMonster {
            return UserMonster(
                monsterId = monsterId,
                userId = userId,
            )
        }
    }
}
