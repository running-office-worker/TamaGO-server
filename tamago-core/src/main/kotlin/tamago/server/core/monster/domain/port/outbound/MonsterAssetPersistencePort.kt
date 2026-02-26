package tamago.server.core.monster.domain.port.outbound

import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId
import java.time.LocalDateTime

interface MonsterAssetPersistencePort {
    fun existsByMonsterIdAndAssetType(
        monsterId: MonsterId,
        assetType: AssetType,
    ): Boolean

    fun existsByUpdatedAtAfter(since: LocalDateTime): Boolean

    fun findAllByMonsterIdsAndAssetType(
        monsterIds: List<MonsterId>,
        assetType: AssetType,
    ): List<MonsterAsset>

    fun findAll(): List<MonsterAsset>

    fun save(monsterAsset: MonsterAsset): MonsterAsset
}
