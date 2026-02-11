package tamago.server.core.monster

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId

interface MonsterCommandUseCase {
    fun initMonster(userId: UserId, monsterId: MonsterId): OwnedMonster
    fun createMonsterAsset(monsterId: MonsterId, assetType: AssetType, assetKey: String): MonsterAsset
}
