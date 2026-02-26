package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "몬스터 에셋 직접 업로드 응답")
data class UploadMonsterAssetResponse(
    @field:Schema(description = "조회용 Presigned URL", requiredMode = Schema.RequiredMode.REQUIRED)
    val previewUrl: String,
    @field:Schema(
        description = "S3 에셋 경로",
        example = "dev/monster/1/abc.png",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val assetKey: String,
)
