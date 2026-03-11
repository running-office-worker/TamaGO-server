package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.monster.MonsterXpResultDto
import tamago.server.core.running.domain.aggregate.Running

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
            running: Running,
            xpResult: MonsterXpResultDto,
            waypoints: List<WaypointDto>,
        ): RunningFinishQueryDto =
            RunningFinishQueryDto(
                pace = running.pace?.toInt() ?: 0,
                cadence = running.cadence ?: 0,
                elapsedTime = running.elapsedTime ?: 0,
                totalCalories = running.calories ?: 0,
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
