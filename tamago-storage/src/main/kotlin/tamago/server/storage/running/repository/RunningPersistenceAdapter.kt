package tamago.server.storage.running.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.inbound.query.MonsterRunningStatsQueryDto
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.storage.monster.entity.MonsterEntity
import tamago.server.storage.monster.entity.OwnedMonsterEntity
import tamago.server.storage.running.entity.RunningEntity
import tamago.server.storage.running.mapper.RunningMapper
import tamago.server.storage.support.findAll
import tamago.server.storage.support.findOne
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class RunningPersistenceAdapter(
    private val runningJpaRepository: RunningJpaRepository,
    private val entityManager: EntityManager,
    private val jpqlRenderContext: JpqlRenderContext,
) : RunningPersistencePort {
    override fun save(running: Running): Running =
        RunningMapper.toDomain(runningJpaRepository.save(RunningMapper.toEntity(running)))!!

    override fun findLastByUserId(userId: UserId): Running? =
        runningJpaRepository
            .findTopByUserIdAndDeletedAtIsNullOrderByFinishedAtDesc(userId.value)
            ?.let { RunningMapper.toDomain(it) }

    override fun findAllByUserIdAndMonth(
        userId: UserId,
        year: Int,
        month: Int,
    ): List<Running> {
        val monthStart = LocalDate.of(year, month, 1).atStartOfDay()
        val monthEnd = monthStart.plusMonths(1)
        return runningJpaRepository
            .findAllByUserIdAndStartedAtGreaterThanEqualAndStartedAtLessThanAndDeletedAtIsNull(
                userId = userId.value,
                monthStart = monthStart,
                monthEnd = monthEnd,
            ).mapNotNull { RunningMapper.toDomain(it) }
    }

    override fun sumDistanceByUserId(userId: UserId): Double {
        val query =
            jpql {
                select(coalesce(sum(path(RunningEntity::distance)), 0.0))
                    .from(entity(RunningEntity::class))
                    .where(
                        path(RunningEntity::userId)
                            .equal(userId.value)
                            .and(path(RunningEntity::deletedAt).isNull()),
                    )
            }
        return entityManager.findOne<Double>(query, jpqlRenderContext) ?: 0.0
    }

    override fun sumDurationMinutesByUserId(userId: UserId): Long {
        val query =
            jpql {
                select(coalesce(sum(path(RunningEntity::elapsedTime)), 0))
                    .from(entity(RunningEntity::class))
                    .where(
                        path(RunningEntity::userId)
                            .equal(userId.value)
                            .and(path(RunningEntity::deletedAt).isNull()),
                    )
            }
        val totalSeconds = entityManager.findOne<Long>(query, jpqlRenderContext) ?: 0L
        return totalSeconds / 60
    }

    override fun findStatsByUserIdAndOwnedMonsterIds(
        userId: UserId,
        ownedMonsterIds: List<Long>,
    ): List<MonsterRunningStatsQueryDto> {
        if (ownedMonsterIds.isEmpty()) return emptyList()

        val query =
            jpql {
                selectNew<RunningStatsByMonsterRow>(
                    path(RunningEntity::ownedMonsterId),
                    coalesce(sum(path(RunningEntity::distance)), 0.0),
                    count(path(RunningEntity::id)),
                    coalesce(sum(path(RunningEntity::elapsedTime)), 0L),
                ).from(entity(RunningEntity::class))
                    .where(
                        path(RunningEntity::userId)
                            .equal(userId.value)
                            .and(path(RunningEntity::ownedMonsterId).`in`(ownedMonsterIds))
                            .and(path(RunningEntity::deletedAt).isNull()),
                    ).groupBy(path(RunningEntity::ownedMonsterId))
            }

        val rows = entityManager.findAll<RunningStatsByMonsterRow>(query, jpqlRenderContext)

        return rows.map { row ->
            MonsterRunningStatsQueryDto(
                ownedMonsterId = row.ownedMonsterId,
                totalDistance = row.totalDistance,
                runCount = row.runCount.toInt(),
                totalTimeMinutes = row.totalElapsedSeconds / 60,
            )
        }
    }

    override fun findLastMonsterNicknameByUserId(userId: UserId): String? {
        val query =
            jpql {
                select(path(OwnedMonsterEntity::monster)(MonsterEntity::nickname))
                    .from(
                        entity(RunningEntity::class),
                        join(OwnedMonsterEntity::class)
                            .on(path(OwnedMonsterEntity::id).equal(path(RunningEntity::ownedMonsterId))),
                    ).where(
                        path(RunningEntity::userId)
                            .equal(userId.value)
                            .and(path(RunningEntity::deletedAt).isNull()),
                    ).orderBy(path(RunningEntity::finishedAt).desc())
            }
        return entityManager.findOne<String>(query, jpqlRenderContext)
    }

    override fun findRunningDatesByUserId(
        userId: UserId,
        since: LocalDateTime,
    ): List<LocalDate> {
        val query =
            jpql {
                select(path(RunningEntity::startedAt))
                    .from(entity(RunningEntity::class))
                    .where(
                        path(RunningEntity::userId)
                            .equal(userId.value)
                            .and(path(RunningEntity::startedAt).greaterThanOrEqualTo(since))
                            .and(path(RunningEntity::deletedAt).isNull()),
                    ).orderBy(path(RunningEntity::startedAt).desc())
            }

        return entityManager
            .findAll<LocalDateTime>(query, jpqlRenderContext)
            .map { it.toLocalDate() }
            .distinct()
    }
}
