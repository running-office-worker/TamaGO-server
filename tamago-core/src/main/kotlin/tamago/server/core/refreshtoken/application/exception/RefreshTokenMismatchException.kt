package tamago.server.core.refreshtoken.application.exception

import tamago.server.core.common.exception.BusinessException

class RefreshTokenMismatchException : BusinessException(
    RefreshTokenExceptionCode.REFRESH_TOKEN_MISMATCH,
)
