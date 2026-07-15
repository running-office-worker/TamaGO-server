package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.monster.MonsterQuery
import tamago.server.core.running.domain.vo.RunningPlanId
import java.time.LocalDateTime

data class RunningPlanResultQueryDto(
    val runningPlanId: RunningPlanId,
    val backgroundAssets: List<BackgroundAssetDetail>,
) {
    data class BackgroundAssetDetail(
        val assetType: String,
        val fileName: String,
        val url: String,
        val lastModifiedAt: LocalDateTime,
        val metadata: MonsterQuery.BackgroundAsset.Metadata?,
    )
}
