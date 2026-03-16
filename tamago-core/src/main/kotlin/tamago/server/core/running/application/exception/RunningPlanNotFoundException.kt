package tamago.server.core.running.application.exception

import tamago.server.core.common.exception.BusinessException

class RunningPlanNotFoundException :
    BusinessException(
        RunningExceptionCode.RUNNING_PLAN_NOT_FOUND,
    )
