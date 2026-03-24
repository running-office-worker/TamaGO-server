package tamago.server.storage.monster.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.storage.monster.entity.MonsterAssetEntity
import tamago.server.storage.monster.entity.MonsterEntity
import tamago.server.storage.monster.mapper.MonsterAssetMapper
import tamago.server.storage.support.findAll
import tamago.server.storage.support.findOne
import java.time.LocalDateTime

@Repository
class MonsterAssetPersistenceAdapter(
    private val monsterAssetJpaRepository: MonsterAssetJpaRepository,
    private val monsterJpaRepository: MonsterJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MonsterAssetPersistencePort {
    @Suppress("SqlSourceToSinkFlow")
    override fun existsByMonsterIdAndAssetType(
        monsterId: MonsterId,
        assetType: AssetType,
    ): Boolean {
        val query =
            jpql {
                select(
                    entity(MonsterAssetEntity::class),
                ).from(
                    entity(MonsterAssetEntity::class),
                    join(MonsterAssetEntity::monster),
                ).where(
                    and(
                        path(MonsterAssetEntity::monster)(MonsterEntity::id).eq(monsterId.value),
                        path(MonsterAssetEntity::assetType).eq(assetType),
                        path(MonsterAssetEntity::deletedAt).isNull(),
                    ),
                )
            }

        val rendered = JpqlRenderer().render(query, jpqlRenderContext)

        return entityManager
            .createQuery(rendered.query, MonsterAssetEntity::class.java)
            .apply { rendered.params.forEach { (key, value) -> setParameter(key, value) } }
            .setMaxResults(1)
            .resultList
            .isNotEmpty()
    }

    override fun findAllByMonsterIdsAndAssetType(
        monsterIds: List<MonsterId>,
        assetType: AssetType,
    ): List<MonsterAsset> {
        if (monsterIds.isEmpty()) return emptyList()

        val query =
            jpql {
                select(
                    entity(MonsterAssetEntity::class),
                ).from(
                    entity(MonsterAssetEntity::class),
                    join(MonsterAssetEntity::monster),
                ).where(
                    and(
                        path(MonsterAssetEntity::monster)(MonsterEntity::id).`in`(monsterIds.map { it.value }),
                        path(MonsterAssetEntity::assetType).eq(assetType),
                        path(MonsterAssetEntity::deletedAt).isNull(),
                    ),
                )
            }

        return entityManager
            .findAll<MonsterAssetEntity>(query, jpqlRenderContext)
            .mapNotNull { MonsterAssetMapper.toDomain(it) }
    }

    override fun existsByUpdatedAtAfter(since: LocalDateTime): Boolean =
        monsterAssetJpaRepository.existsByUpdatedAtAfterAndDeletedAtIsNull(since)

    override fun findAll(): List<MonsterAsset> =
        monsterAssetJpaRepository.findAllByDeletedAtIsNull().mapNotNull { MonsterAssetMapper.toDomain(it) }

    override fun save(monsterAsset: MonsterAsset): MonsterAsset {
        val monsterEntity = monsterJpaRepository.findById(monsterAsset.monsterId.value).orElseThrow()
        val entity = MonsterAssetMapper.toEntity(monsterAsset, monsterEntity)
        val saved = monsterAssetJpaRepository.save(entity)
        return MonsterAssetMapper.toDomain(saved)!!
    }

    override fun findByMonsterIdAndAssetType(
        monsterId: MonsterId,
        assetType: AssetType,
    ): MonsterAsset? {
        val query =
            jpql {
                select(
                    entity(MonsterAssetEntity::class),
                ).from(
                    entity(MonsterAssetEntity::class),
                ).where(
                    and(
                        path(MonsterAssetEntity::monster)(MonsterEntity::id).eq(monsterId.value),
                        path(MonsterAssetEntity::assetType).eq(assetType),
                        path(MonsterAssetEntity::deletedAt).isNull(),
                    ),
                )
            }

        return entityManager
            .findOne<MonsterAssetEntity>(query, jpqlRenderContext)
            ?.let { MonsterAssetMapper.toDomain(it) }
    }

    override fun deleteById(monsterAsset: MonsterAsset) {
        monsterAssetJpaRepository.deleteById(monsterAsset.id!!.value)
    }
}
