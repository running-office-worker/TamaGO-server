package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.port.inbound.query.MonsterAssetBundleQueryDto
import tamago.server.core.monster.domain.vo.AssetMetadata
import java.time.LocalDateTime

@Schema(description = "몬스터 에셋 번들 응답")
data class MonsterAssetBundleResponse(
    @field:Schema(description = "몬스터 그룹 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterGroupId: Long,
    @field:Schema(description = "몬스터 그룹 코드", example = "TURTLE", requiredMode = Schema.RequiredMode.REQUIRED)
    val code: String,
    @field:Schema(description = "몬스터 그룹 이름", example = "거북이")
    val name: String?,
    @field:Schema(description = "그룹 배경 에셋 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val backgroundAssets: List<AssetDetailResponse>,
    @field:Schema(description = "몬스터별 에셋 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsters: List<MonsterAssetDetailResponse>,
) {
    @Schema(description = "몬스터별 에셋 상세")
    data class MonsterAssetDetailResponse(
        @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        val monsterId: Long,
        @field:Schema(description = "진화 단계", example = "1")
        val evolutionStage: Int?,
        @field:Schema(description = "에셋 목록", requiredMode = Schema.RequiredMode.REQUIRED)
        val assets: List<AssetDetailResponse>,
    ) {
        companion object {
            fun from(dto: MonsterAssetBundleQueryDto.MonsterAssetDetail): MonsterAssetDetailResponse =
                MonsterAssetDetailResponse(
                    monsterId = dto.monsterId,
                    evolutionStage = dto.evolutionStage,
                    assets = dto.assets.map { AssetDetailResponse.from(it) },
                )
        }
    }

    @Schema(description = "에셋 상세 정보")
    data class AssetDetailResponse(
        @field:Schema(
            description = "에셋 타입",
            example = "M_PNG",
            requiredMode = Schema.RequiredMode.REQUIRED,
        )
        val assetType: String,
        @field:Schema(description = "파일명", example = "M_PNG.png", requiredMode = Schema.RequiredMode.REQUIRED)
        val fileName: String,
        @field:Schema(description = "Presigned URL", requiredMode = Schema.RequiredMode.REQUIRED)
        val url: String,
        @field:Schema(description = "마지막 수정 일시", requiredMode = Schema.RequiredMode.REQUIRED)
        val lastModifiedAt: LocalDateTime,
        @field:Schema(description = "에셋 부가 메타데이터")
        val metadata: AssetMetadata?,
    ) {
        companion object {
            fun from(dto: MonsterAssetBundleQueryDto.AssetDetail): AssetDetailResponse =
                AssetDetailResponse(
                    assetType = dto.assetType,
                    fileName = dto.fileName,
                    url = dto.url,
                    lastModifiedAt = dto.lastModifiedAt,
                    metadata = dto.metadata,
                )
        }
    }

    companion object {
        fun from(dto: MonsterAssetBundleQueryDto): MonsterAssetBundleResponse =
            MonsterAssetBundleResponse(
                monsterGroupId = dto.monsterGroupId,
                code = dto.code,
                name = dto.name,
                backgroundAssets = dto.backgroundAssets.map { AssetDetailResponse.from(it) },
                monsters = dto.monsters.map { MonsterAssetDetailResponse.from(it) },
            )
    }
}
