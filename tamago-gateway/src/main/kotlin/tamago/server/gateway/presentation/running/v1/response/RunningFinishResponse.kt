package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.port.inbound.query.RunningFinishQueryDto

@Schema(description = "러닝 종료 응답")
data class RunningFinishResponse(
    @field:Schema(description = "페이스 (초/km)", example = "360", requiredMode = Schema.RequiredMode.REQUIRED)
    val pace: Int,
    @field:Schema(description = "케이던스 (spm)", example = "170", requiredMode = Schema.RequiredMode.REQUIRED)
    val cadence: Int,
    @field:Schema(description = "경과 시간 (초)", example = "2700", requiredMode = Schema.RequiredMode.REQUIRED)
    val elapsedTime: Int,
    @field:Schema(description = "총 소모 칼로리 (kcal)", example = "350", requiredMode = Schema.RequiredMode.REQUIRED)
    val totalCalories: Int,
    @field:Schema(description = "거리 기준 일회성 칭호", requiredMode = Schema.RequiredMode.REQUIRED)
    val distanceTitle: DistanceTitleResponse,
) {
    @Schema(description = "거리 기준 일회성 칭호")
    data class DistanceTitleResponse(
        @field:Schema(description = "칭호", example = "모험 러너", requiredMode = Schema.RequiredMode.REQUIRED)
        val title: String,
        @field:Schema(
            description = "말풍선 문구",
            example = "5km 돌파! 우리 오늘 꽤 멀리 왔어!",
            requiredMode = Schema.RequiredMode.REQUIRED,
        )
        val message: String,
    )

    companion object {
        fun from(dto: RunningFinishQueryDto) =
            RunningFinishResponse(
                pace = dto.pace,
                cadence = dto.cadence,
                elapsedTime = dto.elapsedTime,
                totalCalories = dto.totalCalories,
                distanceTitle = resolveDistanceTitle(dto.distance),
            )

        private fun resolveDistanceTitle(distance: Double): DistanceTitleResponse =
            when {
                distance >= 10.0 -> DistanceTitleResponse("레전드 러너", "10km 돌파! 오늘은 레전드야!")
                distance >= 7.0 -> DistanceTitleResponse("강철 러너", "강철 러너 등극! 오늘 진짜 잘 달렸어!")
                distance >= 5.0 -> DistanceTitleResponse("모험 러너", "5km 돌파! 우리 오늘 꽤 멀리 왔어!")
                distance >= 3.0 -> DistanceTitleResponse("성장 러너", "성장 러너 칭호 획득! 나도 더 컸어!")
                distance >= 2.0 -> DistanceTitleResponse("꾸준한 러너", "오늘도 꾸준히 달렸어, 좋아!")
                distance >= 1.0 -> DistanceTitleResponse("가벼운 러너", "1km 돌파! 몸 풀기 제대로 했네!")
                else -> DistanceTitleResponse("첫 걸음 러너", "첫 걸음 성공! 오늘도 같이 달렸어!")
            }
    }
}
