package tamago.server.core.refreshtoken.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.refreshtoken.application.service.RefreshTokenCommandService

private val logger = KotlinLogging.logger {}

@Component
class RefreshTokenEventListener(
    private val refreshTokenCommandService: RefreshTokenCommandService,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserDeleted(event: UserDeletedEvent) {
        try {
            refreshTokenCommandService.delete(event.userId)
        } catch (e: Exception) {
            logger.error(e) { "회원 탈퇴 후 리프레시 토큰 삭제 오류 - userId: ${event.userId}" }
        }
    }
}
