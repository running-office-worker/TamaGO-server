package tamago.server.gateway.presentation.monster.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.aggregate.OwnedMonster

@Schema(description = "해금된 몬스터(알) 응답")
data class UnlockedMonsterResponse(
    @field:Schema(description = "소유 몬스터 ID", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    val ownedMonsterId: Long,

    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,
) {
    companion object {
        fun from(ownedMonster: OwnedMonster): UnlockedMonsterResponse =
            UnlockedMonsterResponse(
                ownedMonsterId = ownedMonster.id!!.value,
                monsterId = ownedMonster.monsterId.value,
            )
    }
}
