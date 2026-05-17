package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterGroupAssetId
import tamago.server.core.monster.domain.vo.MonsterGroupId
import java.time.LocalDateTime

class MonsterGroupAsset(
    val id: MonsterGroupAssetId? = null,
    val monsterGroupId: MonsterGroupId,
    val monsterGroupCode: String? = null,
    val monsterGroupName: String? = null,
    val assetKey: String? = null,
    val assetName: String? = null,
    val assetType: AssetType? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
)
