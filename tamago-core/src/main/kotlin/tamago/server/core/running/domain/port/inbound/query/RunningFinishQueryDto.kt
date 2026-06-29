package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.monster.MonsterQuery
import java.time.LocalDateTime

data class RunningFinishQueryDto(
    val pace: Int,
    val cadence: Int,
    val elapsedTime: Int,
    val totalCalories: Int,
    val distance: Double,
    val ownedMonsterId: OwnedMonsterId,
    val finishedAt: LocalDateTime,
    val backgroundAssets: List<BackgroundAssetDetail> = emptyList(),
) {
    data class BackgroundAssetDetail(
        val assetType: String,
        val fileName: String,
        val url: String,
        val lastModifiedAt: LocalDateTime,
        val metadata: MonsterQuery.BackgroundAsset.Metadata?,
    )

    companion object {
        fun of(
            pace: Double?,
            cadence: Int?,
            elapsedTime: Int?,
            totalCalories: Int?,
            distance: Double?,
            ownedMonsterId: Long?,
            finishedAt: LocalDateTime?,
        ): RunningFinishQueryDto =
            RunningFinishQueryDto(
                pace = pace?.toInt() ?: 0,
                cadence = cadence ?: 0,
                elapsedTime = elapsedTime ?: 0,
                totalCalories = totalCalories ?: 0,
                distance = distance ?: 0.0,
                ownedMonsterId = OwnedMonsterId(requireNotNull(ownedMonsterId) { "ownedMonsterId is required" }),
                finishedAt = requireNotNull(finishedAt) { "finishedAt is required" },
            )
    }
}
