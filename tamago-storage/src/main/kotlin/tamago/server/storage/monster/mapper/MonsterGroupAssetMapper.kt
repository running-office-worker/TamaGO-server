package tamago.server.storage.monster.mapper

import tamago.server.core.monster.domain.aggregate.MonsterGroupAsset
import tamago.server.core.monster.domain.vo.MonsterGroupAssetId
import tamago.server.core.monster.domain.vo.MonsterGroupId
import tamago.server.storage.monster.entity.MonsterGroupAssetEntity
import tamago.server.storage.monster.entity.MonsterGroupEntity

object MonsterGroupAssetMapper {
    fun toEntity(
        domain: MonsterGroupAsset,
        monsterGroup: MonsterGroupEntity,
    ): MonsterGroupAssetEntity =
        MonsterGroupAssetEntity(
            id = domain.id?.value,
            monsterGroup = monsterGroup,
            assetKey = domain.assetKey,
            assetName = domain.assetName,
            assetType = domain.assetType,
            metadata = domain.metadata,
        )

    fun toDomain(entity: MonsterGroupAssetEntity?): MonsterGroupAsset? {
        if (entity == null) return null

        return MonsterGroupAsset(
            id = entity.id?.let { MonsterGroupAssetId(it) },
            monsterGroupId = MonsterGroupId(entity.monsterGroup.id!!),
            monsterGroupCode = entity.monsterGroup.code,
            monsterGroupName = entity.monsterGroup.name,
            assetKey = entity.assetKey,
            assetName = entity.assetName,
            assetType = entity.assetType,
            metadata = entity.metadata,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
