package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId

interface MonsterAssetPersistencePort {
    fun existsByMonsterIdAndAssetType(monsterId: MonsterId, assetType: AssetType): Boolean
    fun save(monsterAsset: MonsterAsset): MonsterAsset
}
