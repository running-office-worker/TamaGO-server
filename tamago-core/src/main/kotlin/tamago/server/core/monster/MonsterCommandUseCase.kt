package tamago.server.core.monster

import tamago.server.core.common.vo.OwnedMonsterId

interface MonsterCommandUseCase {
    fun addEarnedXp(
        ownedMonsterId: OwnedMonsterId,
        xp: Int,
    )
}
