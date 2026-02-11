package tamago.server.core.monster

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId

interface MonsterQueryUseCase {
    fun get(id: MonsterId): Monster
    fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster
    fun getEvolutionChain(monsterId: MonsterId): List<Monster>
    fun getRandomFirstStageMonster(): Monster
    fun checkMonsterAssetNotExists(monsterId: MonsterId, assetType: AssetType)
}
