package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.running.domain.aggregate.Running

data class RunningFinishQueryDto(
    val pace: Int,
    val cadence: Int,
    val elapsedTime: Int,
    val totalCalories: Int,
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

    companion object {
        fun of(
            running: Running,
            ownedMonster: OwnedMonster,
            evolutionChain: List<Monster>,
            earnedXp: Int,
        ): RunningFinishQueryDto {
            return RunningFinishQueryDto(
                pace = running.pace?.toInt() ?: 0,
                cadence = running.cadence ?: 0,
                elapsedTime = running.elapsedTime ?: 0,
                totalCalories = running.calories ?: 0,
                originXp = ownedMonster.havingXp ?: 0,
                earnedXp = earnedXp,
                evolutionStages = evolutionChain.mapIndexed { index, monster ->
                    EvolutionStageDto(
                        stage = index + 1,
                        monsterId = monster.id!!.value,
                        evolutionXp = monster.evolutionXp,
                        current = monster.id == ownedMonster.monsterId,
                    )
                },
            )
        }
    }
}
