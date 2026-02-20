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

    @field:Schema(description = "원래 경험치", example = "500", requiredMode = Schema.RequiredMode.REQUIRED)
    val originXp : Int,

    @field:Schema(description = "현재 경험치", example = "700", requiredMode = Schema.RequiredMode.REQUIRED)
    val earnedXp: Int,

    @field:Schema(description = "진화 단계별 몬스터 정보 (최대 4단계)", requiredMode = Schema.RequiredMode.REQUIRED)
    val evolutionStages: List<EvolutionStageResponse>,
) {
    @Schema(description = "진화 단계 정보 응답")
    data class EvolutionStageResponse(
        @field:Schema(description = "진화 단계 (1~4)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        val stage: Int,

        @field:Schema(description = "해당 단계 몬스터 ID", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        val monsterId: Long,

        @field:Schema(description = "다음 단계로 진화에 필요한 경험치", example = "200", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        val evolutionXp: Int?,

        @field:Schema(description = "현재 단계 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
        val current: Boolean,
    )

    companion object {
        fun from(dto: RunningFinishQueryDto) = RunningFinishResponse(
            pace = dto.pace,
            cadence = dto.cadence,
            elapsedTime = dto.elapsedTime,
            totalCalories = dto.totalCalories,
            originXp = dto.originXp,
            earnedXp = dto.earnedXp,
            evolutionStages = dto.evolutionStages.map { stage ->
                EvolutionStageResponse(
                    stage = stage.stage,
                    monsterId = stage.monsterId,
                    evolutionXp = stage.evolutionXp,
                    current = stage.current,
                )
            },
        )
    }
}
