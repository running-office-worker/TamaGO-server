package tamago.server.core.running.domain.port.inbound.command

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.running.domain.vo.RunningPlanId
import java.time.LocalDateTime

data class SaveRunningCommandDto(
    val userId: UserId,
    val weight: Double,
    val distance: Double,
    val elevationGain: Double,
    val heartbeat: Int?,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    val runningPlanId: RunningPlanId,
    val ownedMonsterId: OwnedMonsterId,
    val waypoints: List<WaypointDto> = emptyList(),
) {
    data class WaypointDto(
        val latitude: Double,
        val longitude: Double,
    )
}
