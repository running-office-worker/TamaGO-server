package tamago.server.gateway.presentation.running.v1.request

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import java.time.LocalDateTime

@Schema(description = "러닝 데이터 저장 바디")
data class RunningRequest(
    @field:Schema(description = "러닝 거리 (km)", example = "5.23", requiredMode = Schema.RequiredMode.REQUIRED)
    val distance: Double,

    @field:Schema(description = "누적 고도 상승 (m)", example = "120.5", requiredMode = Schema.RequiredMode.REQUIRED)
    val elevationGain: Double,

    @field:Schema(description = "평균 심박수 (bpm)", example = "145", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    val heartbeat: Int,

    @field:Schema(description = "러닝 시작 시간", example = "2025-01-15T08:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
    val startedAt: LocalDateTime,

    @field:Schema(description = "러닝 종료 시간", example = "2025-01-15T09:15:00", requiredMode = Schema.RequiredMode.REQUIRED)
    val finishedAt: LocalDateTime,

    @field:Schema(description = "소유한 몬스터 ID", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
    val ownedMonsterId: OwnedMonsterId,
)

fun RunningRequest.toCommand(userId: UserId, weight: Double) = SaveRunningCommandDto(
    userId = userId,
    weight = weight,
    distance = distance,
    elevationGain = elevationGain,
    heartbeat = heartbeat,
    startedAt = startedAt,
    finishedAt = finishedAt,
    ownedMonsterId = ownedMonsterId,
)
