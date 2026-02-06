package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.vo.MonsterAssetId
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterAssetEntity
import tamago.server.core.monster.infrastructure.entity.MonsterEntity

object MonsterAssetMapper {
    fun toEntity(domain: MonsterAsset, monster: MonsterEntity): MonsterAssetEntity {
        return MonsterAssetEntity(
            id = domain.id?.value,
            monster = monster,
            assetKey = domain.assetKey,
            assetType = domain.assetType,
        )
    }

    fun toDomain(entity: MonsterAssetEntity?): MonsterAsset? {
        if (entity == null) return null

        return MonsterAsset(
            id = entity.id?.let { MonsterAssetId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            assetKey = entity.assetKey,
            assetType = entity.assetType,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
