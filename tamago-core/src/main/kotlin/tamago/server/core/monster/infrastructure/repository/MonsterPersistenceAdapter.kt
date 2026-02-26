package tamago.server.core.monster.infrastructure.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.common.jdsl.findAll
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.monster.infrastructure.mapper.MonsterMapper

@Repository
class MonsterPersistenceAdapter(
    private val monsterJpaRepository: MonsterJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : MonsterPersistencePort {
    override fun findById(id: MonsterId): Monster? =
        MonsterMapper.toDomain(monsterJpaRepository.findById(id.value).orElse(null))

    override fun findAllByPreviousMonsterIdIsNull(): List<Monster> =
        monsterJpaRepository.findAllByPreviousMonsterIsNull().mapNotNull { MonsterMapper.toDomain(it) }

    override fun findAllWithUnlockPolicies(): List<Monster> {
        val query =
            jpql {
                selectDistinct(
                    entity(MonsterEntity::class),
                ).from(
                    entity(MonsterEntity::class),
                    leftFetchJoin(MonsterEntity::unlockPolicies),
                )
            }

        return entityManager
            .findAll<MonsterEntity>(query, jpqlRenderContext)
            .mapNotNull { MonsterMapper.toDomain(it) }
    }

    override fun findAllFirstStageWithUnlockPolicies(): List<Monster> {
        val query =
            jpql {
                selectDistinct(
                    entity(MonsterEntity::class),
                ).from(
                    entity(MonsterEntity::class),
                    leftFetchJoin(MonsterEntity::unlockPolicies),
                ).where(
                    path(MonsterEntity::previousMonster).isNull(),
                )
            }

        return entityManager
            .findAll<MonsterEntity>(query, jpqlRenderContext)
            .mapNotNull { MonsterMapper.toDomain(it) }
    }

    override fun findAllDefaultMonsters(): List<Monster> {
        val query =
            jpql {
                selectDistinct(
                    entity(MonsterEntity::class),
                ).from(
                    entity(MonsterEntity::class),
                    leftFetchJoin(MonsterEntity::unlockPolicies),
                ).where(
                    path(MonsterEntity::previousMonster).isNull(),
                )
            }

        return entityManager
            .findAll<MonsterEntity>(query, jpqlRenderContext)
            .filter { it.unlockPolicies.isEmpty() }
            .mapNotNull { MonsterMapper.toDomain(it) }
    }
}
