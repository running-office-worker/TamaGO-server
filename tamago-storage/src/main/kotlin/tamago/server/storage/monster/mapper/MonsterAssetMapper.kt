package tamago.server.storage.monster.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.vo.MonsterAssetId
import tamago.server.storage.monster.entity.MonsterAssetEntity
import tamago.server.storage.monster.entity.MonsterEntity

object MonsterAssetMapper {
    fun toEntity(
        domain: MonsterAsset,
        monster: MonsterEntity,
    ): MonsterAssetEntity =
        MonsterAssetEntity(
            id = domain.id?.value,
            monster = monster,
            assetKey = domain.assetKey,
            assetName = domain.assetName,
            assetType = domain.assetType,
        )

    fun toDomain(entity: MonsterAssetEntity?): MonsterAsset? {
        if (entity == null) return null

        return MonsterAsset(
            id = entity.id?.let { MonsterAssetId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            assetKey = entity.assetKey,
            assetName = entity.assetName,
            assetType = entity.assetType,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
