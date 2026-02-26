package tamago.server.core.user.application.exception

import tamago.server.core.common.exception.BusinessException

class EmailAlreadyExistsException :
    BusinessException(
        UserExceptionCode.EMAIL_ALREADY_EXISTS,
    )
