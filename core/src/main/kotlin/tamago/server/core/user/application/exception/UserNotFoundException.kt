package tamago.server.core.user.application.exception

import tamago.server.core.common.exception.BusinessException

class UserNotFoundException : BusinessException(
    UserExceptionCode.USER_NOT_FOUND,
) {
}