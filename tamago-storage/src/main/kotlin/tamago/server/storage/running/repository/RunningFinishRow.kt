package tamago.server.storage.running.repository

data class RunningFinishRow(
    val pace: Double?,
    val cadence: Int?,
    val elapsedTime: Int?,
    val totalCalories: Int?,
)
