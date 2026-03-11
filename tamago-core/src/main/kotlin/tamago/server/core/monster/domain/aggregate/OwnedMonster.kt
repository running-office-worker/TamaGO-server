package tamago.server.core.monster.domain.aggregate

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import java.time.LocalDateTime

class OwnedMonster(
    val id: OwnedMonsterId? = null,
    val monsterId: MonsterId,
    val userId: UserId,
    havingXp: Int? = null,
    status: OwnedMonsterStatus? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    var havingXp: Int? = havingXp
        private set

    var status: OwnedMonsterStatus? = status
        private set

    fun addXp(xp: Int) {
        havingXp = (havingXp ?: 0) + xp
    }

    companion object {
        fun create(
            monsterId: MonsterId,
            userId: UserId,
            havingXp: Int? = null,
        ): OwnedMonster =
            OwnedMonster(
                monsterId = monsterId,
                userId = userId,
                havingXp = havingXp,
                status = OwnedMonsterStatus.OWNED,
            )
    }
}
