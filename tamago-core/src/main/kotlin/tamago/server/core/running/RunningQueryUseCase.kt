package tamago.server.core.running

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import java.time.LocalDateTime

interface RunningQueryUseCase {
    fun getLastFinishedAt(userId: UserId): LocalDateTime?

    fun getStatsByOwnedMonsterIds(
        userId: UserId,
        ownedMonsterIds: List<Long>,
    ): List<MonsterRunningStatsQueryDto>
}
