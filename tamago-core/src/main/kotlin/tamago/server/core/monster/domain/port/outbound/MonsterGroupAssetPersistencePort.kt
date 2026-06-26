package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.MonsterGroupAsset

interface MonsterGroupAssetPersistencePort {
    fun findAll(): List<MonsterGroupAsset>
}
