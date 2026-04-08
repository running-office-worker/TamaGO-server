package tamago.server.core.notification.application.exception

import tamago.server.core.common.exception.BusinessException

class FcmTokenNotFoundException :
    BusinessException(
        NotificationExceptionCode.FCM_TOKEN_NOT_FOUND,
    )
