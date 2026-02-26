package tamago.server.gateway.presentation.monster.v1.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.monster.domain.port.inbound.query.MonsterDexQueryDto

@Schema(description = "몬스터 도감 응답, NULL 인 필드는 응답에서 제외됩니다.")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MonsterDexResponse(
    @field:Schema(description = "몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val monsterId: Long,
    @field:Schema(description = "몬스터 닉네임", example = "타마알", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    val nickname: String?,
    @field:Schema(
        description = "해금 조건 설명",
        example = "10km를 달리면 해금됩니다",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
    )
    val unlockDescription: String?,
    @field:Schema(description = "보유 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    val owned: Boolean,
    @field:Schema(description = "보유 몬스터 정보 (보유 시에만 존재)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    val ownedMonster: OwnedMonsterInfo?,
) {
    @Schema(description = "보유 몬스터 정보")
    data class OwnedMonsterInfo(
        @field:Schema(description = "소유 몬스터 ID", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        val ownedMonsterId: Long,
        @field:Schema(description = "보유 경험치", example = "100", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        val havingXp: Int?,
        @field:Schema(
            description = "UNLOCKED 는 해금 되었지만 소유하지 않은 상태, OWNED 는 유저가 몬스터를 소유한 상태입니다. ",
            example = "OWNED",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        )
        val status: String?,
    ) {
        companion object {
            fun from(dto: MonsterDexQueryDto.OwnedMonsterInfo): OwnedMonsterInfo =
                OwnedMonsterInfo(
                    ownedMonsterId = dto.ownedMonsterId,
                    havingXp = dto.havingXp,
                    status = dto.status,
                )
        }
    }

    companion object {
        fun from(dto: MonsterDexQueryDto): MonsterDexResponse =
            MonsterDexResponse(
                monsterId = dto.monsterId,
                nickname = dto.nickname,
                unlockDescription = dto.unlockDescription,
                owned = dto.owned,
                ownedMonster = dto.ownedMonster?.let { OwnedMonsterInfo.from(it) },
            )
    }
}
