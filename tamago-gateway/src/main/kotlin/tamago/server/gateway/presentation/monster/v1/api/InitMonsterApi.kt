package tamago.server.gateway.presentation.monster.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.response.InitMonsterResponse

@Tag(name = "Init Monster API", description = "테스트용 몬스터 초기화 API (local, dev 환경에서만 사용 가능)")
interface InitMonsterApi {
    @Operation(summary = "랜덤 1단계 몬스터 초기화", description = "인증된 유저에게 랜덤 1단계 몬스터를 소유시킵니다.")
    fun initRandomMonster(user: User): CustomResponse<InitMonsterResponse>
}
