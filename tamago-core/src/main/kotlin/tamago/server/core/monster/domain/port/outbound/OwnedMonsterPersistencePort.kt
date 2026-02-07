package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.vo.OwnedMonsterId

interface OwnedMonsterPersistencePort {
    fun findById(id: OwnedMonsterId): OwnedMonster?
}
