package tamago.server.storage.running.repository

import java.time.LocalDateTime

data class RunningFinishRow(
    val pace: Double?,
    val cadence: Int?,
    val elapsedTime: Int?,
    val totalCalories: Int?,
    val distance: Double?,
    val ownedMonsterId: Long?,
    val finishedAt: LocalDateTime?,
)
