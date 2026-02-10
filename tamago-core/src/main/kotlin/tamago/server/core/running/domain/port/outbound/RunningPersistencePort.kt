package tamago.server.core.running.domain.port.outbound

import tamago.server.core.running.domain.aggregate.Running

interface RunningPersistencePort {
    fun save(running: Running): Running
}
