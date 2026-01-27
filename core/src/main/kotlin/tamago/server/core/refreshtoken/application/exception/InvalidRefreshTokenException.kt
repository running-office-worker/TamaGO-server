package tamago.server.core.refreshtoken.application.exception

import tamago.server.core.common.exception.BusinessException

class InvalidRefreshTokenException : BusinessException(
    RefreshTokenExceptionCode.INVALID_REFRESH_TOKEN,
)
