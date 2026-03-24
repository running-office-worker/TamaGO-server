package tamago.server.core.monster.application.exception

import tamago.server.core.common.exception.BusinessException

class MonsterAssetNotFoundException :
    BusinessException(
        MonsterExceptionCode.MONSTER_ASSET_NOT_FOUND,
    )
