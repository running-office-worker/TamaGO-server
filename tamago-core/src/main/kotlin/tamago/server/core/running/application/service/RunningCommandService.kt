package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.running.RunningCommandUseCase
import tamago.server.core.running.domain.util.RunningCalculator
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort

@Service
class RunningCommandService(
    private val runningPersistencePort: RunningPersistencePort,
) : RunningCommandUseCase {

    override fun save(command: SaveRunningCommandDto): Running {
        val pace = RunningCalculator.calculatePace(command.distance, command.startedAt, command.finishedAt)
        val cadence = RunningCalculator.calculateCadence(command.distance, command.startedAt, command.finishedAt)
        val calories = RunningCalculator.calculateCalories(command.distance, command.weight, command.startedAt, command.finishedAt)

        val running = Running.create(
            userId = command.userId,
            ownedMonsterId = command.ownedMonsterId,
            pace = pace,
            cadence = cadence,
            calories = calories,
            distance = command.distance,
            elevationGain = command.elevationGain,
            heartbeat = command.heartbeat,
            startedAt = command.startedAt,
            finishedAt = command.finishedAt,
        )

        return runningPersistencePort.save(running)
    }
}
