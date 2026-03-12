package tamago.server.core.running.domain.port.inbound.command

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import java.time.LocalDateTime

data class SaveRunningCommandDto(
    val userId: UserId,
    val weight: Double,
    val distance: Double,
    val elevationGain: Double,
    val heartbeat: Int?,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    val ownedMonsterId: OwnedMonsterId,
    val waypoints: List<WaypointDto> = emptyList(),
) {
    data class WaypointDto(
        val latitude: Double,
        val longitude: Double,
    )
}
