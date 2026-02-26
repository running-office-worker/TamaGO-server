package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "몬스터 초기화 응답")
data class InitMonsterResponse(
    @field:Schema(description = "소유 몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val ownedMonsterId: Long,
    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,
    @field:Schema(description = "몬스터 닉네임", example = "타마알", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    val nickname: String?,
    @field:Schema(description = "소유 상태", example = "OWNED", requiredMode = Schema.RequiredMode.REQUIRED)
    val status: String,
)
