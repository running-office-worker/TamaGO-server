package tamago.server.core.monster

import java.time.LocalDateTime

sealed interface MonsterQuery {
    data class XpResult(
        val originXp: Int,
        val earnedXp: Int,
        val evolutionStages: List<EvolutionStage>,
    ) : MonsterQuery {
        data class EvolutionStage(
            val stage: Int,
            val monsterId: Long,
            val evolutionXp: Int?,
            val current: Boolean,
        )
    }

    data class BackgroundAsset(
        val assetType: String,
        val fileName: String,
        val assetKey: String,
        val lastModifiedAt: LocalDateTime,
        val metadata: Metadata?,
    ) : MonsterQuery {
        data class Metadata(
            val backgroundColor: String?,
            val startHour: Int?,
            val endHour: Int?,
        )
    }
}
