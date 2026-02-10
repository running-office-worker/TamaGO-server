package tamago.server.core.monster

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster

interface MonsterCommandUseCase {
    fun initRandomMonster(userId: UserId): OwnedMonster
}
