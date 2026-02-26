package tamago.server.core.running.application.exception

import tamago.server.core.common.exception.BusinessException

class InvalidRunningDataException :
    BusinessException(
        RunningExceptionCode.INVALID_RUNNING_DATA,
    )
