package tamago.server.core.running.domain.port.inbound.command

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId

data class CreateRunningPlanCommandDto(
    val userId: UserId,
    val goalDistance: Double,
    val ownedMonsterId: OwnedMonsterId,
)
