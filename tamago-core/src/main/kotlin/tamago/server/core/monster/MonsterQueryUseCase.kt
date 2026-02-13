package tamago.server.core.monster

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId

interface MonsterQueryUseCase {
    fun get(id: MonsterId): Monster
    fun getAllWithUnlockPolicies(): List<Monster>
    fun getAllFirstStageWithUnlockPolicies(): List<Monster>
    fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster
    fun getOwnedMonstersByUserId(userId: UserId): List<OwnedMonster>
    fun getMonsterAssetsByMonsterIds(monsterIds: List<MonsterId>, assetType: AssetType): List<MonsterAsset>
    fun getEvolutionChain(monsterId: MonsterId): List<Monster>
    fun getRandomFirstStageMonster(): Monster
    fun checkMonsterAssetNotExists(monsterId: MonsterId, assetType: AssetType)
    fun getMonsterPngUrl(monsterId: MonsterId): String?
}
