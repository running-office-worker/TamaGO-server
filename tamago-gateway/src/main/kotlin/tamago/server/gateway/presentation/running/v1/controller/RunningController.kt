package tamago.server.gateway.presentation.running.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.running.RunningFacade
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.running.v1.request.RunningRequest
import tamago.server.gateway.presentation.running.v1.request.toCommand
import tamago.server.gateway.presentation.running.v1.response.MonthlyRunningResponse
import tamago.server.gateway.presentation.running.v1.response.RunningFinishResponse

@Tag(name = "Running API", description = "러닝 데이터 API")
@RestController
class RunningController(
    private val runningFacade: RunningFacade,
) {
    @Operation(summary = "러닝 데이터 저장", description = "사용자의 러닝 데이터를 저장하고, 계산된 러닝 데이터와 몬스터 경험치를 반환합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v1/running")
    fun saveRunningData(
        @CurrentUser user: User,
        @RequestBody @Valid request: RunningRequest,
    ): CustomResponse<RunningFinishResponse> {
        val result = runningFacade.saveRunningData(request.toCommand(user.id!!, user.weight))
        return CustomResponse.created(RunningFinishResponse.from(result))
    }

    @Operation(summary = "월별 러닝 데이터 조회", description = "해당 월의 일별 러닝 데이터와 월간 요약을 반환합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/running/monthly")
    fun getMonthlyRunningData(
        @CurrentUser user: User,
        @RequestParam @Parameter(description = "조회 연도", example = "2026") year: Int,
        @RequestParam @Parameter(description = "조회 월 (1~12)", example = "2") month: Int,
    ): CustomResponse<MonthlyRunningResponse> {
        val result = runningFacade.getMonthlyRunningData(user.id!!, year, month)
        return CustomResponse.ok(MonthlyRunningResponse.from(result))
    }
}
