package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime

class OwnedMonster(
    val id: OwnedMonsterId? = null,
    val monsterId: MonsterId,
    val userId: UserId,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(monsterId: MonsterId, userId: UserId): OwnedMonster {
            return OwnedMonster(
                monsterId = monsterId,
                userId = userId,
            )
        }
    }
}
