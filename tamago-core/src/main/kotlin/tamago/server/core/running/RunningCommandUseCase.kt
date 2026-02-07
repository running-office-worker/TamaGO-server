package tamago.server.core.running

import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto

interface RunningCommandUseCase {
    fun save(command: SaveRunningCommandDto): Running
}
