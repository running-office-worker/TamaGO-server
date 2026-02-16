package tamago.server.gateway.presentation.running.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.running.v1.request.RunningRequest
import tamago.server.gateway.presentation.running.v1.response.MonthlyRunningResponse
import tamago.server.gateway.presentation.running.v1.response.RunningFinishResponse

@Tag(name = "Running API", description = "러닝 데이터 API")
interface RunningApi {
    @Operation(summary = "러닝 데이터 저장", description = "사용자의 러닝 데이터를 저장하고, 계산된 러닝 데이터와 몬스터 경험치를 반환합니다.")
    fun saveRunningData(user: User, request: RunningRequest): CustomResponse<RunningFinishResponse>

    @Operation(summary = "월별 러닝 데이터 조회", description = "해당 월의 일별 러닝 데이터와 월간 요약을 반환합니다.")
    fun getMonthlyRunningData(
        user: User,
        @Parameter(description = "조회 연도", example = "2026") year: Int,
        @Parameter(description = "조회 월 (1~12)", example = "2") month: Int,
    ): CustomResponse<MonthlyRunningResponse>
}
