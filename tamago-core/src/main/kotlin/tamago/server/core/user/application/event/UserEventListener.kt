package tamago.server.core.user.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.running.domain.event.RunningCompletedEvent
import tamago.server.core.user.application.service.UserCommandService
import tamago.server.core.user.application.service.UserQueryService

private val logger = KotlinLogging.logger {}

@Component
class UserEventListener(
    private val userQueryService: UserQueryService,
    private val userCommandService: UserCommandService,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onRunningCompleted(event: RunningCompletedEvent) {
        try {
            val user = userQueryService.get(event.userId)
            userCommandService.addRunningDistance(user, event.distance)
        } catch (e: Exception) {
            logger.error(e) { "러닝 완료 후 총 거리 업데이트 오류 - userId: ${event.userId}" }
        }
    }
}
