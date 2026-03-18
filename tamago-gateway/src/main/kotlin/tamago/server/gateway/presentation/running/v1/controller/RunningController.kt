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
import tamago.server.gateway.presentation.running.v1.request.RunningPlanRequest
import tamago.server.gateway.presentation.running.v1.request.RunningRequest
import tamago.server.gateway.presentation.running.v1.request.toCommand
import tamago.server.gateway.presentation.running.v1.response.MonsterRunningStatsResponse
import tamago.server.gateway.presentation.running.v1.response.MonthlyRunningResponse
import tamago.server.gateway.presentation.running.v1.response.RunningFinishResponse
import tamago.server.gateway.presentation.running.v1.response.RunningPlanResponse

@Tag(name = "Running API", description = "러닝 데이터 API")
@RestController
class RunningController(
    private val runningFacade: RunningFacade,
) {
    @Operation(summary = "러닝 목표 생성", description = "목표 거리를 설정하여 러닝 계획을 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v1/running/plan")
    fun makeRunningPlan(
        @CurrentUser user: User,
        @RequestBody @Valid request: RunningPlanRequest,
    ): CustomResponse<RunningPlanResponse> {
        val runningPlanId = runningFacade.makeRunningPlan(request.toCommand(user.id!!))
        return CustomResponse.created(RunningPlanResponse.from(runningPlanId))
    }

    @Operation(summary = "러닝 데이터 저장", description = "사용자의 러닝 데이터를 저장하고, 계산된 러닝 데이터와 몬스터 경험치를 반환합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v1/running")
    fun finishRun(
        @CurrentUser user: User,
        @RequestBody @Valid request: RunningRequest,
    ): CustomResponse<RunningFinishResponse> {
        val result = runningFacade.finishRun(request.toCommand(user.id!!, user.weight))
        return CustomResponse.created(RunningFinishResponse.from(result))
    }

    @Operation(summary = "몬스터별 러닝 통계 조회", description = "소유 몬스터 ID 목록으로 몬스터별 러닝 통계를 반환합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/running/stats")
    fun getStatsByOwnedMonsterIds(
        @CurrentUser user: User,
        @RequestParam @Parameter(description = "소유 몬스터 ID 목록", example = "[1,2,3]") ownedMonsterIds: List<Long>,
    ): CustomResponse<List<MonsterRunningStatsResponse>> {
        val result = runningFacade.getStatsByOwnedMonsterIds(user.id!!, ownedMonsterIds)
        return CustomResponse.ok(result.map { MonsterRunningStatsResponse.from(it) })
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
