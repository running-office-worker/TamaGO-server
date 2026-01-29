package tamago.server.core.user.application.exception

import tamago.server.core.common.exception.BusinessException

class InvalidCredentialsException : BusinessException(
    UserExceptionCode.INVALID_CREDENTIALS,
)
