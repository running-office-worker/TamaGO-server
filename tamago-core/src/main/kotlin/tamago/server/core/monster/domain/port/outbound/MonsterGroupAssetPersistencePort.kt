package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.MonsterGroupAsset
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterGroupId

interface MonsterGroupAssetPersistencePort {
    fun findAll(): List<MonsterGroupAsset>

    fun findAllByMonsterGroupIdAndAssetTypes(
        monsterGroupId: MonsterGroupId,
        assetTypes: List<AssetType>,
    ): List<MonsterGroupAsset>
}
