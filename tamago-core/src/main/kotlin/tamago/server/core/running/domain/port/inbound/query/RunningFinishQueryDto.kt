package tamago.server.core.running.domain.port.inbound.query

data class RunningFinishQueryDto(
    val pace: Int,
    val cadence: Int,
    val elapsedTime: Int,
    val totalCalories: Int,
) {
    companion object {
        fun of(
            pace: Double?,
            cadence: Int?,
            elapsedTime: Int?,
            totalCalories: Int?,
        ): RunningFinishQueryDto =
            RunningFinishQueryDto(
                pace = pace?.toInt() ?: 0,
                cadence = cadence ?: 0,
                elapsedTime = elapsedTime ?: 0,
                totalCalories = totalCalories ?: 0,
            )
    }
}
