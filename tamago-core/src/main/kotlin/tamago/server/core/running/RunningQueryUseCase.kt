package tamago.server.core.running

import tamago.server.core.common.vo.UserId
import java.time.LocalDateTime

interface RunningQueryUseCase {
    fun getLastFinishedAt(userId: UserId): LocalDateTime?
}
