package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.port.inbound.query.MonthlyRunningQueryDto
import java.time.LocalDate

@Schema(description = "월별 러닝 데이터 응답")
data class MonthlyRunningResponse(
    @field:Schema(description = "일별 러닝 데이터 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val dailyRunnings: List<DailyRunningResponse>,
    @field:Schema(description = "월간 요약", requiredMode = Schema.RequiredMode.REQUIRED)
    val summary: MonthlySummaryResponse,
) {
    @Schema(description = "일별 러닝 데이터")
    data class DailyRunningResponse(
        @field:Schema(description = "날짜", example = "2025-07-15", requiredMode = Schema.RequiredMode.REQUIRED)
        val date: LocalDate,
        @field:Schema(description = "해당 날짜의 러닝 목록", requiredMode = Schema.RequiredMode.REQUIRED)
        val runs: List<RunDetailResponse>,
    ) {
        companion object {
            fun from(dto: MonthlyRunningQueryDto.DailyRunningDto): DailyRunningResponse =
                DailyRunningResponse(
                    date = dto.date,
                    runs = dto.runs.map { RunDetailResponse.from(it) },
                )
        }
    }

    @Schema(description = "러닝 상세 정보")
    data class RunDetailResponse(
        @field:Schema(description = "페이스 (초/km)", example = "360", requiredMode = Schema.RequiredMode.REQUIRED)
        val pace: Int,
        @field:Schema(description = "소모 칼로리 (kcal)", example = "350", requiredMode = Schema.RequiredMode.REQUIRED)
        val calories: Int,
        @field:Schema(description = "경과 시간 (초)", example = "2700", requiredMode = Schema.RequiredMode.REQUIRED)
        val elapsedTime: Int,
    ) {
        companion object {
            fun from(dto: MonthlyRunningQueryDto.RunDetailDto): RunDetailResponse =
                RunDetailResponse(
                    pace = dto.pace,
                    calories = dto.calories,
                    elapsedTime = dto.elapsedTime,
                )
        }
    }

    @Schema(description = "월간 요약 정보")
    data class MonthlySummaryResponse(
        @field:Schema(description = "총 거리 (km)", example = "42.5", requiredMode = Schema.RequiredMode.REQUIRED)
        val totalDistance: Double,
        @field:Schema(description = "러닝 횟수", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
        val runCount: Int,
        @field:Schema(description = "총 러닝 시간 (분)", example = "450", requiredMode = Schema.RequiredMode.REQUIRED)
        val totalTimeMinutes: Int,
    ) {
        companion object {
            fun from(dto: MonthlyRunningQueryDto.MonthlySummaryDto): MonthlySummaryResponse =
                MonthlySummaryResponse(
                    totalDistance = dto.totalDistance,
                    runCount = dto.runCount,
                    totalTimeMinutes = dto.totalTimeMinutes,
                )
        }
    }

    companion object {
        fun from(dto: MonthlyRunningQueryDto): MonthlyRunningResponse =
            MonthlyRunningResponse(
                dailyRunnings = dto.dailyRunnings.map { DailyRunningResponse.from(it) },
                summary = MonthlySummaryResponse.from(dto.summary),
            )
    }
}
