package tamago.server.core.running.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.common.event.UserRestoredEvent
import tamago.server.core.running.application.service.RunningCommandService
import tamago.server.core.running.application.service.RunningQueryService

private val logger = KotlinLogging.logger {}

@Component
class RunningEventListener(
    private val runningQueryService: RunningQueryService,
    private val runningCommandService: RunningCommandService,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserDeleted(event: UserDeletedEvent) {
        try {
            val runnings = runningQueryService.findAllByUserId(event.userId)
            runnings.forEach { runningCommandService.delete(it) }

            val plans = runningQueryService.findAllRunningPlansByUserId(event.userId)
            plans.forEach { runningCommandService.deleteRunningPlan(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 탈퇴 후 러닝 데이터 삭제 오류 - userId: ${event.userId}" }
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserRestored(event: UserRestoredEvent) {
        try {
            val runnings = runningQueryService.findAllDeletedByUserId(event.userId)
            runnings.forEach { runningCommandService.restore(it) }

            val plans = runningQueryService.findAllDeletedRunningPlansByUserId(event.userId)
            plans.forEach { runningCommandService.restoreRunningPlan(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 복구 후 러닝 데이터 복구 오류 - userId: ${event.userId}" }
        }
    }
}
