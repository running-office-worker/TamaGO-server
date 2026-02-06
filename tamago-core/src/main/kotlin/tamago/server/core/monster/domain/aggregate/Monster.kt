package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

class Monster(
    val id: MonsterId? = null,
    val nextMonsterId: MonsterId? = null,
    val nickname: String? = null,
    val evolutionXp: Int? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            nextMonsterId: MonsterId? = null,
            nickname: String? = null,
            evolutionXp: Int? = null,
        ): Monster {
            return Monster(
                nextMonsterId = nextMonsterId,
                nickname = nickname,
                evolutionXp = evolutionXp,
            )
        }
    }
}
