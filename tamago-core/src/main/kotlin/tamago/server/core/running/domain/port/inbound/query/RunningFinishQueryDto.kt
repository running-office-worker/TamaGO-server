package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.running.domain.aggregate.Running
import java.time.Duration
import java.time.LocalDateTime

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
            currentMonster: Monster,
            evolutionChain: List<Monster>,
            distance: Double,
            startedAt: LocalDateTime,
            finishedAt: LocalDateTime,
        ): RunningFinishQueryDto {
            val elapsedSeconds = Duration.between(startedAt, finishedAt).seconds.toInt()

            return RunningFinishQueryDto(
                pace = running.pace?.toInt() ?: 0,
                cadence = running.cadence ?: 0,
                elapsedTime = elapsedSeconds,
                totalCalories = running.calories ?: 0,
                originXp = ownedMonster.havingXp ?: 0,
                earnedXp = currentMonster.evolutionPolicy?.calculateXp(distance) ?: 0, // TODO: earnedXp 저장
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
