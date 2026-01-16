package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

class Monster(
    val id: MonsterId? = null,
    val nextMonsterId: MonsterId? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(nextMonsterId: MonsterId? = null): Monster {
            return Monster(
                nextMonsterId = nextMonsterId,
            )
        }
    }
}
