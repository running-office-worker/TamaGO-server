package tamago.server.core.running.infrastructure.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.common.jdsl.findOne
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.core.running.infrastructure.entity.RunningEntity
import tamago.server.core.running.infrastructure.mapper.RunningMapper
import java.time.LocalDate

@Repository
class RunningPersistenceAdapter(
    private val runningJpaRepository: RunningJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : RunningPersistencePort {

    override fun save(running: Running): Running =
        RunningMapper.toDomain(runningJpaRepository.save(RunningMapper.toEntity(running)))!!

    override fun findAllByUserIdAndMonth(userId: UserId, year: Int, month: Int): List<Running> {
        val monthStart = LocalDate.of(year, month, 1).atStartOfDay()
        val monthEnd = monthStart.plusMonths(1)
        return runningJpaRepository
            .findAllByUserIdAndStartedAtGreaterThanEqualAndStartedAtLessThanAndDeletedAtIsNull(
                userId = userId.value,
                monthStart = monthStart,
                monthEnd = monthEnd,
            )
            .mapNotNull { RunningMapper.toDomain(it) }
    }

    override fun sumDistanceByUserId(userId: UserId): Double {
        val query = jpql {
            select(coalesce(sum(path(RunningEntity::distance)), 0.0))
                .from(entity(RunningEntity::class))
                .where(
                    path(RunningEntity::userId).equal(userId.value)
                        .and(path(RunningEntity::deletedAt).isNull()),
                )
        }
        return entityManager.findOne<Double>(query, jpqlRenderContext) ?: 0.0
    }
}
