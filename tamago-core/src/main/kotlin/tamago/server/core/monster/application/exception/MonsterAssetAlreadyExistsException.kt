package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class MonsterAssetAlreadyExistsException : BusinessException(
    MonsterExceptionCode.MONSTER_ASSET_ALREADY_EXISTS,
)
