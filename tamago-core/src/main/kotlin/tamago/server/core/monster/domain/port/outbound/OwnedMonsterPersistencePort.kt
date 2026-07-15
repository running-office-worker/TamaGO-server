package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import java.time.LocalDateTime

interface OwnedMonsterPersistencePort {
    fun findById(id: OwnedMonsterId): OwnedMonster?

    fun findAllByUserId(userId: UserId): List<OwnedMonster>

    fun findAllByUserIdAndCreatedAfter(
        userId: UserId,
        after: LocalDateTime,
    ): List<OwnedMonster>

    fun save(ownedMonster: OwnedMonster): OwnedMonster

    fun findAllDeletedByUserId(userId: UserId): List<OwnedMonster>

    fun hardDeleteAllByUserId(userId: UserId)
}
