package tamago.server.core.monster

data class MonsterXpResultDto(
    val originXp: Int,
    val earnedXp: Int,
    val evolutionStages: List<EvolutionStageDto>,
) {
    data class EvolutionStageDto(
        val stage: Int,
        val monsterId: Long,
        val evolutionXp: Int?,
        val current: Boolean,
    )
}
