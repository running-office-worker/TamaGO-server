package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.vo.MonsterId

interface MonsterPersistencePort {
    fun findById(id: MonsterId): Monster?

    fun findAllByPreviousMonsterIdIsNull(): List<Monster>

    fun findAllWithUnlockPolicies(): List<Monster>

    fun findAllFirstStageWithUnlockPolicies(): List<Monster>

    fun findAllDefaultMonsters(): List<Monster>
}
