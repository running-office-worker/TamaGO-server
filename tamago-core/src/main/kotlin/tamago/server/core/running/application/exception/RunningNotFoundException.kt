package tamago.server.core.running.application.exception

import tamago.server.core.common.exception.BusinessException

class RunningNotFoundException :
    BusinessException(
        RunningExceptionCode.RUNNING_NOT_FOUND,
    )
