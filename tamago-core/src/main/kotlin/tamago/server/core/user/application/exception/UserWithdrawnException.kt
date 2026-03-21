package tamago.server.core.user.application.exception

import tamago.server.core.common.exception.BusinessException

class UserWithdrawnException :
    BusinessException(
        UserExceptionCode.USER_WITHDRAWN,
    )
