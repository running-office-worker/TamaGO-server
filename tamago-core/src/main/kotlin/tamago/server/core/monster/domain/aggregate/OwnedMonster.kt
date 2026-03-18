package tamago.server.core.monster.domain.aggregate

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import java.time.LocalDateTime

class OwnedMonster(
    val id: OwnedMonsterId? = null,
    monsterId: MonsterId,
    val userId: UserId,
    havingXp: Int? = null,
    status: OwnedMonsterStatus? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    deletedAt: LocalDateTime? = null,
) {
    var monsterId: MonsterId = monsterId
        private set

    var havingXp: Int? = havingXp
        private set

    var status: OwnedMonsterStatus? = status
        private set

    var deletedAt: LocalDateTime? = deletedAt
        private set

    fun addXp(xp: Int) {
        havingXp = (havingXp ?: 0) + xp
    }

    fun evolve(nextMonsterId: MonsterId) {
        monsterId = nextMonsterId
    }

    fun delete() {
        this.deletedAt = LocalDateTime.now()
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
