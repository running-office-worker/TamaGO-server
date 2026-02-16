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
    val havingXp: Int? = null,
    val status: OwnedMonsterStatus? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    fun addXp(xp: Int): OwnedMonster {
        return OwnedMonster(
            id = id,
            monsterId = monsterId,
            userId = userId,
            havingXp = (havingXp ?: 0) + xp,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt,
        )
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
