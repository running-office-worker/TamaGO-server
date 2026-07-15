package tamago.server.core.monster

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import java.time.LocalDateTime

interface MonsterQueryUseCase {
    fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster

    fun getEvolutionChain(monsterId: MonsterId): List<Monster>

    fun getOwnedMonsterMappings(userId: UserId): List<OwnedMonster>

    fun getOwnedMonstersAfter(
        userId: UserId,
        after: LocalDateTime,
    ): List<OwnedMonster>

    fun hasMonsterAssetUpdates(lastLoginAt: LocalDateTime?): Boolean

    fun calculateEarnedXp(
        ownedMonsterId: OwnedMonsterId,
        distance: Double,
    ): MonsterQuery.XpResult

    fun getRunningBackgroundAssets(
        ownedMonsterId: OwnedMonsterId,
        hour: Int,
    ): List<MonsterQuery.BackgroundAsset>
}
