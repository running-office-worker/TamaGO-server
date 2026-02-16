package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
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

    fun own() {
        status = OwnedMonsterStatus.OWNED
        havingXp = havingXp ?: 0
    }

    fun addXp(xp: Int) {
        havingXp = (havingXp ?: 0) + xp
    }

    companion object {
        fun create(
            monsterId: MonsterId,
            userId: UserId,
            havingXp: Int? = null,
            status: OwnedMonsterStatus? = null,
        ): OwnedMonster {
            return OwnedMonster(
                monsterId = monsterId,
                userId = userId,
                havingXp = havingXp,
                status = status,
            )
        }
    }
}
