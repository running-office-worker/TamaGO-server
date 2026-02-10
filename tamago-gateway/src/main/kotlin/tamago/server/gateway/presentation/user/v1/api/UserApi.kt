package tamago.server.gateway.presentation.user.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.presentation.user.v1.request.GoalKiloRequest
import tamago.server.gateway.presentation.user.v1.request.NicknameRequest
import tamago.server.gateway.presentation.user.v1.response.MeResponse
import tamago.server.gateway.presentation.user.v1.response.RunningDataResponse
import tamago.server.gateway.common.response.CustomResponse

@Tag(name = "User API", description = "유저 관련 API")
interface UserApi {
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 유저의 정보를 반환합니다.")
    fun me(user: User): CustomResponse<MeResponse>

    @Operation(summary = "애칭 설정하기", description = "타마고 애칭을 설정합니다.")
    fun giveNickname(user: User, request: NicknameRequest): CustomResponse<Void>

    @Operation(summary = "하루 러닝 목표 설정", description = "하루 러닝 목표를 정합니다.")
    fun setRunningGoal(user: User, request: GoalKiloRequest): CustomResponse<Void>

    @Operation(summary = "누적 러닝 거리와 러닝 일수를 반환", description = "누적 러닝 거리와 러닝 일수를 반환합니다.")
    fun getRunningData(user: User): CustomResponse<RunningDataResponse>
}
