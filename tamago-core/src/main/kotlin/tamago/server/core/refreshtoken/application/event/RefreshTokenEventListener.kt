package tamago.server.core.refreshtoken.application.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.refreshtoken.application.service.RefreshTokenCommandService

@Component
class RefreshTokenEventListener(
    private val refreshTokenCommandService: RefreshTokenCommandService,
) {
    @EventListener
    fun onUserDeleted(event: UserDeletedEvent) {
        refreshTokenCommandService.delete(event.userId)
    }
}
