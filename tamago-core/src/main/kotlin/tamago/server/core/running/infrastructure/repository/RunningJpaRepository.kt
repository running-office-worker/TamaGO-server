package tamago.server.core.running.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.running.infrastructure.entity.RunningEntity
import java.time.LocalDateTime

interface RunningJpaRepository : JpaRepository<RunningEntity, Long> {
    fun findAllByUserIdAndStartedAtGreaterThanEqualAndStartedAtLessThanAndDeletedAtIsNull(
        userId: Long,
        monthStart: LocalDateTime,
        monthEnd: LocalDateTime,
    ): List<RunningEntity>

    fun findTopByUserIdAndDeletedAtIsNullOrderByFinishedAtDesc(userId: Long): RunningEntity?
}
