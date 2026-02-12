package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.vo.OwnedMonsterId

interface OwnedMonsterPersistencePort {
    fun findById(id: OwnedMonsterId): OwnedMonster?
    fun findAllByUserId(userId: UserId): List<OwnedMonster>
    fun save(ownedMonster: OwnedMonster): OwnedMonster
}
