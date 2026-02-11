package tamago.server.core.monster.infrastructure.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterAssetEntity
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.monster.infrastructure.mapper.MonsterAssetMapper

@Repository
class MonsterAssetPersistenceAdapter(
    private val monsterAssetJpaRepository: MonsterAssetJpaRepository,
    private val monsterJpaRepository: MonsterJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MonsterAssetPersistencePort {

    @Suppress("SqlSourceToSinkFlow")
    override fun existsByMonsterIdAndAssetType(monsterId: MonsterId, assetType: AssetType): Boolean {
        val query = jpql {
            select(
                entity(MonsterAssetEntity::class),
            ).from(
                entity(MonsterAssetEntity::class),
                join(MonsterAssetEntity::monster),
            ).where(
                and(
                    path(MonsterAssetEntity::monster)(MonsterEntity::id).eq(monsterId.value),
                    path(MonsterAssetEntity::assetType).eq(assetType),
                ),
            )
        }

        val rendered = JpqlRenderer().render(query, jpqlRenderContext)

        return entityManager.createQuery(rendered.query, MonsterAssetEntity::class.java)
            .apply { rendered.params.forEach { (key, value) -> setParameter(key, value) } }
            .setMaxResults(1)
            .resultList
            .isNotEmpty()
    }

    override fun save(monsterAsset: MonsterAsset): MonsterAsset {
        val monsterEntity = monsterJpaRepository.findById(monsterAsset.monsterId.value).orElseThrow()
        val entity = MonsterAssetMapper.toEntity(monsterAsset, monsterEntity)
        val saved = monsterAssetJpaRepository.save(entity)
        return MonsterAssetMapper.toDomain(saved)!!
    }
}
