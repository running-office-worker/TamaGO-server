package tamago.server.core.monster

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
}
