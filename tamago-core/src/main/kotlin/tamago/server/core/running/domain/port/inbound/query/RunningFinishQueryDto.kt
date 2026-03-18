package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.monster.MonsterQuery

data class RunningFinishQueryDto(
    val pace: Int,
    val cadence: Int,
    val elapsedTime: Int,
    val totalCalories: Int,
    val originXp: Int,
    val earnedXp: Int,
    val evolutionStages: List<EvolutionStageDto>,
    val waypoints: List<WaypointDto>,
) {
    data class EvolutionStageDto(
        val stage: Int,
        val monsterId: Long,
        val evolutionXp: Int?,
        val current: Boolean,
    )

    data class WaypointDto(
        val latitude: Double,
        val longitude: Double,
    )

    companion object {
        fun of(
            pace: Int,
            cadence: Int,
            elapsedTime: Int,
            totalCalories: Int,
            xpResult: MonsterQuery.XpResult,
            waypoints: List<WaypointDto>,
        ): RunningFinishQueryDto =
            RunningFinishQueryDto(
                pace = pace,
                cadence = cadence,
                elapsedTime = elapsedTime,
                totalCalories = totalCalories,
                originXp = xpResult.originXp,
                earnedXp = xpResult.earnedXp,
                evolutionStages =
                    xpResult.evolutionStages.map {
                        EvolutionStageDto(
                            stage = it.stage,
                            monsterId = it.monsterId,
                            evolutionXp = it.evolutionXp,
                            current = it.current,
                        )
                    },
                waypoints = waypoints,
            )
    }
}
