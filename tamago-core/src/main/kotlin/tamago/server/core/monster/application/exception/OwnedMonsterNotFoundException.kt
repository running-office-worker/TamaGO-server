package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class OwnedMonsterNotFoundException : BusinessException(
    MonsterExceptionCode.OWNED_MONSTER_NOT_FOUND,
)
