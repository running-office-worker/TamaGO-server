package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.Monster

interface MonsterPersistencePort {
    fun findById(id: MonsterId): Monster?

    fun findAllByPreviousMonsterIdIsNull(): List<Monster>

    fun findAllWithUnlockPolicies(): List<Monster>

    fun findAllFirstStageWithUnlockPolicies(): List<Monster>

    fun findDefaultMonster(): Monster?
}
