package tamago.server.core.running.application.service

import org.springframework.stereotype.Service
import tamago.server.core.running.domain.aggregate.RunningPlan
import tamago.server.core.running.domain.port.inbound.command.CreateRunningPlanCommandDto
import tamago.server.core.running.domain.port.outbound.RunningPlanPersistencePort

@Service
class RunningPlanCommandService(
    private val runningPlanPersistencePort: RunningPlanPersistencePort,
) {
    fun create(command: CreateRunningPlanCommandDto): RunningPlan {
        val runningPlan =
            RunningPlan.create(
                userId = command.userId,
                goalDistance = command.goalDistance,
            )
        return runningPlanPersistencePort.save(runningPlan)
    }
}
