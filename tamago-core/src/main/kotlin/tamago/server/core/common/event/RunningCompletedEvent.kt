package tamago.server.core.common.event

import tamago.server.core.common.vo.UserId
import java.time.LocalDateTime

data class RunningCompletedEvent(
    val userId: UserId,
    val startedAt: LocalDateTime,
)
