package tamago.server.core.running

sealed interface RunningQuery {
    data class Streak(
        val isFirstRun: Boolean,
        val streakDays: Int,
        val inactiveDays: Int,
    ) : RunningQuery
}
