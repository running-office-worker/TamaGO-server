package tamago.server.core.common.event

import tamago.server.core.common.vo.UserId
import java.time.LocalDateTime

data class RunningCompletedEvent(
    val userId: UserId,
    val startedAt: LocalDateTime,
    val distance: Double,
    val totalDistance: Double,
    val totalDurationMinutes: Long,
    val isFirstRun: Boolean,
    val streakDays: Int,
    val inactiveDays: Int,
)
