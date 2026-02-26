package tamago.server.gateway.presentation.monster.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED

data class OwnMonsterRequest(
    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = REQUIRED)
    val monsterId: Long,
)
