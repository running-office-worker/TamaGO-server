package tamago.server.storage.monster.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.monster.domain.aggregate.MonsterGroupAsset
import tamago.server.core.monster.domain.port.outbound.MonsterGroupAssetPersistencePort
import tamago.server.storage.monster.entity.MonsterGroupAssetEntity
import tamago.server.storage.monster.mapper.MonsterGroupAssetMapper
import tamago.server.storage.support.findAll

@Repository
class MonsterGroupAssetPersistenceAdapter(
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MonsterGroupAssetPersistencePort {
    override fun findAll(): List<MonsterGroupAsset> {
        val query =
            jpql {
                select(
                    entity(MonsterGroupAssetEntity::class),
                ).from(
                    entity(MonsterGroupAssetEntity::class),
                    join(MonsterGroupAssetEntity::monsterGroup),
                ).where(
                    path(MonsterGroupAssetEntity::deletedAt).isNull(),
                )
            }

        return entityManager
            .findAll<MonsterGroupAssetEntity>(query, jpqlRenderContext)
            .mapNotNull { MonsterGroupAssetMapper.toDomain(it) }
    }
}
