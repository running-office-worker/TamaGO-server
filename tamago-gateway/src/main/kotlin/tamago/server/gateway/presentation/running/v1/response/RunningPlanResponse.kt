package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.MonsterQuery
import tamago.server.core.running.domain.port.inbound.query.RunningPlanResultQueryDto
import java.time.LocalDateTime

@Schema(description = "러닝 계획 응답")
data class RunningPlanResponse(
    @field:Schema(description = "러닝 계획 ID", example = "1")
    val runningPlanId: Long,
    @field:Schema(description = "러닝 배경 에셋 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val backgroundAssets: List<BackgroundAssetResponse>,
) {
    @Schema(description = "러닝 배경 에셋")
    data class BackgroundAssetResponse(
        @field:Schema(description = "에셋 타입", example = "LBG_PNG", requiredMode = Schema.RequiredMode.REQUIRED)
        val assetType: String,
        @field:Schema(description = "파일명", example = "LBG_PNG_1.png", requiredMode = Schema.RequiredMode.REQUIRED)
        val fileName: String,
        @field:Schema(description = "Presigned URL", requiredMode = Schema.RequiredMode.REQUIRED)
        val url: String,
        @field:Schema(description = "마지막 수정 일시", requiredMode = Schema.RequiredMode.REQUIRED)
        val lastModifiedAt: LocalDateTime,
        @field:Schema(description = "에셋 부가 메타데이터")
        val metadata: MonsterQuery.BackgroundAsset.Metadata?,
    ) {
        companion object {
            fun from(dto: RunningPlanResultQueryDto.BackgroundAssetDetail): BackgroundAssetResponse =
                BackgroundAssetResponse(
                    assetType = dto.assetType,
                    fileName = dto.fileName,
                    url = dto.url,
                    lastModifiedAt = dto.lastModifiedAt,
                    metadata = dto.metadata,
                )
        }
    }

    companion object {
        fun from(dto: RunningPlanResultQueryDto): RunningPlanResponse =
            RunningPlanResponse(
                runningPlanId = dto.runningPlanId.value,
                backgroundAssets = dto.backgroundAssets.map { BackgroundAssetResponse.from(it) },
            )
    }
}
