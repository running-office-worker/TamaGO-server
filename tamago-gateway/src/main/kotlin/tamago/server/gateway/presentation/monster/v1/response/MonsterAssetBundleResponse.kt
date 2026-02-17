package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.port.inbound.query.MonsterAssetBundleQueryDto
import java.time.LocalDateTime

@Schema(description = "몬스터 에셋 번들 응답")
data class MonsterAssetBundleResponse(
    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,

    @field:Schema(description = "에셋 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val assets: List<AssetDetailResponse>,
) {
    @Schema(description = "에셋 상세 정보")
    data class AssetDetailResponse(
        @field:Schema(description = "에셋 타입 (png, gif, lottie)", example = "png", requiredMode = Schema.RequiredMode.REQUIRED)
        val assetType: String,

        @field:Schema(description = "Presigned URL", requiredMode = Schema.RequiredMode.REQUIRED)
        val url: String,

        @field:Schema(description = "마지막 수정 일시", requiredMode = Schema.RequiredMode.REQUIRED)
        val lastModifiedAt: LocalDateTime?,
    ) {
        companion object {
            fun from(dto: MonsterAssetBundleQueryDto.AssetDetail): AssetDetailResponse =
                AssetDetailResponse(
                    assetType = dto.assetType,
                    url = dto.url,
                    lastModifiedAt = dto.lastModifiedAt,
                )
        }
    }

    companion object {
        fun from(dto: MonsterAssetBundleQueryDto): MonsterAssetBundleResponse =
            MonsterAssetBundleResponse(
                monsterId = dto.monsterId,
                assets = dto.assets.map { AssetDetailResponse.from(it) },
            )
    }
}
