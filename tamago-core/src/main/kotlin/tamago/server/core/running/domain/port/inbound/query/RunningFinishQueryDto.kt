package tamago.server.core.running.domain.port.inbound.query

data class RunningFinishQueryDto(
    val pace: Int,
    val cadence: Int,
    val elapsedTime: Int,
    val totalCalories: Int,
    val distance: Double,
) {
    companion object {
        fun of(
            pace: Double?,
            cadence: Int?,
            elapsedTime: Int?,
            totalCalories: Int?,
            distance: Double?,
        ): RunningFinishQueryDto =
            RunningFinishQueryDto(
                pace = pace?.toInt() ?: 0,
                cadence = cadence ?: 0,
                elapsedTime = elapsedTime ?: 0,
                totalCalories = totalCalories ?: 0,
                distance = distance ?: 0.0,
            )
    }
}
