package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class MonsterAlreadyOwnedException :
    BusinessException(
        MonsterExceptionCode.MONSTER_ALREADY_OWNED,
    )
