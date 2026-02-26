package tamago.server.core.refreshtoken.application.exception

import tamago.server.core.common.exception.BusinessException

class RefreshTokenNotFoundException :
    BusinessException(
        RefreshTokenExceptionCode.REFRESH_TOKEN_NOT_FOUND,
    )
