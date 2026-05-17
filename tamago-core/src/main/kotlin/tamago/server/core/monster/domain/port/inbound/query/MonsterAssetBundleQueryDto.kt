package tamago.server.core.monster.domain.port.inbound.query

import java.time.LocalDateTime

data class MonsterAssetBundleQueryDto(
    val monsterGroupId: Long,
    val code: String,
    val name: String?,
    val backgroundAssets: List<AssetDetail>,
    val monsters: List<MonsterAssetDetail>,
) {
    data class MonsterAssetDetail(
        val monsterId: Long,
        val evolutionStage: Int?,
        val assets: List<AssetDetail>,
    )

    data class AssetDetail(
        val assetType: String,
        val fileName: String,
        val url: String,
        val lastModifiedAt: LocalDateTime,
    )
}
