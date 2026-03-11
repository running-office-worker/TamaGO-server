package tamago.server.core.running

data class RunningStreakQueryDto(
    val isFirstRun: Boolean,
    val streakDays: Int,
    val inactiveDays: Int,
)
