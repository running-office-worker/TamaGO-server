package tamago.server.core.running.domain.aggregate

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.domain.vo.RunningPlanId
import java.time.LocalDateTime

class Running(
    val id: RunningId? = null,
    val userId: UserId,
    val ownedMonsterId: OwnedMonsterId,
    val runningPlanId: RunningPlanId,
    val pace: Double? = null,
    val cadence: Int? = null,
    val calories: Int? = null,
    val distance: Double? = null,
    val elevationGain: Double? = null,
    val heartbeat: Int? = null,
    val elapsedTime: Int? = null,
    val startedAt: LocalDateTime? = null,
    val finishedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            userId: UserId,
            ownedMonsterId: OwnedMonsterId,
            runningPlanId: RunningPlanId,
            pace: Double,
            cadence: Int,
            calories: Int,
            distance: Double,
            elevationGain: Double,
            heartbeat: Int?,
            elapsedTime: Int,
            startedAt: LocalDateTime,
            finishedAt: LocalDateTime,
        ): Running =
            Running(
                userId = userId,
                ownedMonsterId = ownedMonsterId,
                runningPlanId = runningPlanId,
                pace = pace,
                cadence = cadence,
                calories = calories,
                distance = distance,
                elevationGain = elevationGain,
                heartbeat = heartbeat,
                elapsedTime = elapsedTime,
                startedAt = startedAt,
                finishedAt = finishedAt,
            )
    }
}
