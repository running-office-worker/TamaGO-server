package tamago.server.core.user.application.exception

import tamago.server.core.common.exception.BusinessException

class UserSaveErrorException :
    BusinessException(
        UserExceptionCode.USER_SAVE_ERROR,
    )
