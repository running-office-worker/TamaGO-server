package tamago.server.core.monster.domain.aggregate

import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterAssetId
import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

class MonsterAsset(
    val id: MonsterAssetId? = null,
    val monsterId: MonsterId,
    val assetKey: String? = null,
    val assetType: AssetType? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            monsterId: MonsterId,
            assetKey: String? = null,
            assetType: AssetType? = null,
        ): MonsterAsset {
            return MonsterAsset(
                monsterId = monsterId,
                assetKey = assetKey,
                assetType = assetType,
            )
        }
    }
}
