package tamago.server.gateway.presentation.monster.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.response.MonsterAssetBundleResponse
import tamago.server.gateway.presentation.monster.v1.response.MonsterDexResponse
import tamago.server.gateway.presentation.monster.v1.response.UnlockedMonsterResponse

@Tag(name = "Monster API", description = "몬스터 도감 API")
interface MonsterApi {
    @Operation(summary = "전체 몬스터 도감 조회", description = "전체 몬스터 캐릭터 도감 정보를 조회합니다.")
    fun getMonsterDex(user: User): CustomResponse<List<MonsterDexResponse>>

    @Operation(summary = "해금된 몬스터 목록 조회", description = "해금된 몬스터(알) 목록을 조회합니다.")
    fun getUnlockedMonsters(user: User): CustomResponse<List<UnlockedMonsterResponse>>

    @Operation(summary = "해금된 몬스터 소유", description = "해금된 몬스터를 소유 상태로 변경합니다.")
    fun ownMonster(user: User, ownedMonsterId: Long): CustomResponse<Void>

    @Operation(summary = "전체 몬스터 에셋 조회", description = "모든 몬스터의 에셋(PNG, GIF, LOTTIE) Presigned URL을 조회합니다.")
    fun getAllMonsterAssets(): CustomResponse<List<MonsterAssetBundleResponse>>
}
