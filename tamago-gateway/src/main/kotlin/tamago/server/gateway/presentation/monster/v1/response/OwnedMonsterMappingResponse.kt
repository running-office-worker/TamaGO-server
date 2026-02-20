package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.aggregate.OwnedMonster

@Schema(description = "소유 몬스터 ID 매핑 응답")
data class OwnedMonsterMappingResponse(
    @field:Schema(description = "소유 몬스터 ID", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    val ownedMonsterId: Long,

    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,
) {
    companion object {
        fun from(ownedMonster: OwnedMonster) = OwnedMonsterMappingResponse(
            ownedMonsterId = ownedMonster.id!!.value,
            monsterId = ownedMonster.monsterId.value,
        )
    }
}
