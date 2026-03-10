package tamago.server.core.running.domain.port.inbound.query

data class RunningStreakQueryDto(
    val isFirstRun: Boolean,
    val streakDays: Int,
    val inactiveDays: Int,
)
