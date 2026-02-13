package tamago.server.core.running.domain.port.inbound.query

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
        val monsterImageUrl: String?,
    )

    data class MonthlySummaryDto(
        val totalDistance: Double,
        val runCount: Int,
        val totalTimeMinutes: Int,
    )
}
