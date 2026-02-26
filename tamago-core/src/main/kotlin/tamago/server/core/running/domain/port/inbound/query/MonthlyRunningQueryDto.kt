package tamago.server.core.running.domain.port.inbound.query

import tamago.server.core.running.domain.aggregate.Running
import java.time.Duration
import java.time.LocalDate

data class MonthlyRunningQueryDto(
    val dailyRunnings: List<DailyRunningDto>,
    val summary: MonthlySummaryDto,
) {
    data class DailyRunningDto(
        val date: LocalDate,
        val runs: List<RunDetailDto>,
    )

    data class RunDetailDto(
        val pace: Int,
        val calories: Int,
        val elapsedTime: Int,
    ) {
        companion object {
            fun from(running: Running): RunDetailDto =
                RunDetailDto(
                    pace = running.pace?.toInt() ?: 0,
                    calories = running.calories ?: 0,
                    elapsedTime = Duration.between(running.startedAt, running.finishedAt).seconds.toInt(),
                )
        }
    }

    data class MonthlySummaryDto(
        val totalDistance: Double,
        val runCount: Int,
        val totalTimeMinutes: Int,
    ) {
        companion object {
            fun from(runnings: List<Running>): MonthlySummaryDto =
                MonthlySummaryDto(
                    totalDistance = runnings.sumOf { it.distance ?: 0.0 },
                    runCount = runnings.size,
                    totalTimeMinutes =
                        runnings
                            .sumOf {
                                Duration.between(it.startedAt, it.finishedAt).toMinutes()
                            }.toInt(),
                )
        }
    }
}
