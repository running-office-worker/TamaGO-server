package tamago.server.gateway.presentation.user.v1.controller

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.presentation.user.v1.api.UserApi
import tamago.server.gateway.presentation.user.v1.request.GoalKiloRequest
import tamago.server.gateway.presentation.user.v1.request.NicknameRequest
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.annotation.CurrentUser

@RestController
class UserController(
    private val userCommandUseCase: UserCommandUseCase,
) : UserApi {

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/users/nickname")
    override fun giveNickname(
        @CurrentUser user: User,
        @Valid @RequestBody request: NicknameRequest
    ): CustomResponse<Void> {
        userCommandUseCase.updateNickname(user, request.nickname)
        return CustomResponse.ok()
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/users/running-goal")
    override fun setRunningGoal(
        @CurrentUser user: User,
        @Valid @RequestBody request: GoalKiloRequest
    ): CustomResponse<Void> {
        userCommandUseCase.updateGoalKilo(user, request.goalKilo)
        return CustomResponse.ok()
    }
}
