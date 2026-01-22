package tamago.server.gateway.presentation.user.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.vo.UserId
import tamago.server.gateway.presentation.user.v1.request.SetNicknameRequest
import tamago.server.gateway.response.CustomResponse

@Tag(name = "User API", description = "유저 관련 API")
interface UserApi {
    @Operation(summary = "애칭 설정하기", description = "타마고 애칭을 설정합니다.")
    fun giveNickname(userId: UserId, request: SetNicknameRequest): CustomResponse<Void>
}