package tamago.server.gateway.presentation.running.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto

@Schema(description = "몬스터별 러닝 통계 응답")
data class MonsterRunningStatsResponse(
    @field:Schema(description = "소유 몬스터 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val ownedMonsterId: Long,
    @field:Schema(description = "총 거리 (km)", example = "42.5", requiredMode = Schema.RequiredMode.REQUIRED)
    val totalDistance: Double,
    @field:Schema(description = "러닝 횟수", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    val runCount: Int,
    @field:Schema(description = "총 러닝 시간 (분)", example = "450", requiredMode = Schema.RequiredMode.REQUIRED)
    val totalTimeMinutes: Long,
) {
    companion object {
        fun from(dto: MonsterRunningStatsQueryDto): MonsterRunningStatsResponse =
            MonsterRunningStatsResponse(
                ownedMonsterId = dto.ownedMonsterId,
                totalDistance = dto.totalDistance,
                runCount = dto.runCount,
                totalTimeMinutes = dto.totalTimeMinutes,
            )
    }
}
