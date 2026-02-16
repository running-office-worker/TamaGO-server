package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class MonsterAccessDeniedException : BusinessException(
    MonsterExceptionCode.MONSTER_ACCESS_DENIED,
)
