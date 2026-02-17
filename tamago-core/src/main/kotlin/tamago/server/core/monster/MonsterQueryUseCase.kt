package tamago.server.core.monster

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import java.time.LocalDateTime

interface MonsterQueryUseCase {
    fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster
    fun getEvolutionChain(monsterId: MonsterId): List<Monster>
    fun getMonsterPngUrl(monsterId: MonsterId): String?
    fun getUnlockedMonsters(userId: UserId): List<OwnedMonster>
    fun hasMonsterAssetUpdates(lastLoginAt: LocalDateTime?): Boolean
}
