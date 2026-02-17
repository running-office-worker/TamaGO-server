package tamago.server.core.monster.domain.port.inbound.query

import java.time.LocalDateTime

data class MonsterAssetBundleQueryDto(
    val monsterId: Long,
    val assets: List<AssetDetail>,
) {
    data class AssetDetail(
        val assetType: String,
        val url: String,
        val lastModifiedAt: LocalDateTime,
    )
}
