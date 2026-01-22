package tamago.server.gateway.presentation.user.v1.controller

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserFacade
import tamago.server.core.user.domain.vo.UserId
import tamago.server.gateway.presentation.user.v1.api.UserApi
import tamago.server.gateway.presentation.user.v1.request.SetNicknameRequest
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.annotation.CurrentUserId

@RestController
class UserController(
    private val userFacade: UserFacade
) : UserApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/user/nickname")
    override fun giveNickname(
        @CurrentUserId userId: UserId,
        @Valid @RequestBody request: SetNicknameRequest
    ): CustomResponse<Void> {
        userFacade.giveNickname(userId, request.nickname)
        return CustomResponse.ok()
    }
}