package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class MonsterNotFoundException :
    BusinessException(
        MonsterExceptionCode.MONSTER_NOT_FOUND,
    )
