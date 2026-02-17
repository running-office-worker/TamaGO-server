package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "몬스터 에셋 변경 여부 응답")
data class MonsterAssetUpdateCheckResponse(
    @field:Schema(description = "에셋 변경 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    val hasUpdates: Boolean,
)
