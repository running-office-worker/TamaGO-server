package tamago.server.core.monster

import tamago.server.core.monster.domain.aggregate.OwnedMonster

interface MonsterCommandUseCase {
    fun addEarnedXp(ownedMonster: OwnedMonster, xp: Int): OwnedMonster
}
