package tamago.server.gateway.presentation.user.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserCommandUseCase
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.user.v1.request.NicknameRequest
import tamago.server.gateway.presentation.user.v1.response.MeResponse
import tamago.server.gateway.presentation.user.v1.response.RunningDataResponse
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Tag(name = "User API", description = "유저 관련 API")
@RestController
class UserController(
    private val userCommandUseCase: UserCommandUseCase,
) {
    @Operation(summary = "내 정보 조회", description = "현재 로그인한 유저의 정보를 반환합니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/v1/users/me")
    fun me(
        @CurrentUser user: User,
    ): CustomResponse<MeResponse> = CustomResponse.ok(MeResponse.from(user))

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 처리합니다. 유저 및 모든 연관 데이터가 soft delete 됩니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/api/v1/users/me")
    fun deleteAccount(
        @CurrentUser user: User,
    ): CustomResponse<Void> {
        userCommandUseCase.deleteUser(user)
        return CustomResponse.ok()
    }

    @Operation(summary = "애칭 설정하기", description = "타마고 애칭을 설정합니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/api/v1/users/nickname")
    fun giveNickname(
        @CurrentUser user: User,
        @Valid @RequestBody request: NicknameRequest,
    ): CustomResponse<Void> {
        userCommandUseCase.updateNickname(user, request.nickname)
        return CustomResponse.ok()
    }

    @Operation(summary = "누적 러닝 거리와 러닝 일수를 반환", description = "누적 러닝 거리와 러닝 일수를 반환합니다.")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/v1/users/running-data")
    fun getRunningData(
        @CurrentUser user: User,
    ): CustomResponse<RunningDataResponse> {
        val runningDays =
            user.createdAt?.let {
                ChronoUnit.DAYS.between(it.toLocalDate(), LocalDate.now()) + 1
            } ?: 1
        val response =
            RunningDataResponse(
                totalKilo = user.runningData.totalKilo ?: 0.0,
                runningDays = runningDays,
            )
        return CustomResponse.ok(response)
    }
}
