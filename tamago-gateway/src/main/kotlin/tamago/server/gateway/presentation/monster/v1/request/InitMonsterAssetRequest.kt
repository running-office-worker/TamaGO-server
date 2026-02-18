package tamago.server.gateway.presentation.monster.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.enum.AssetType

@Schema(description = "몬스터 에셋 업로드 URL 생성 요청")
data class InitMonsterAssetRequest(
    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,

    @field:Schema(
        description = "에셋 타입 (SVG, PNG, GIF, LOTTIE 중 하나)",
        example = "SVG",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val assetType: AssetType,
)
