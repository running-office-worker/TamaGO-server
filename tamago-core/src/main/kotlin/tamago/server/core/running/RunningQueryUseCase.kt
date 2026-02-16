package tamago.server.core.running

import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running

interface RunningQueryUseCase {
    fun getMonthlyRunnings(userId: UserId, year: Int, month: Int): List<Running>
}
