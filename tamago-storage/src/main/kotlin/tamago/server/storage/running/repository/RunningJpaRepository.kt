package tamago.server.storage.running.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.running.entity.RunningEntity
import java.time.LocalDateTime

interface RunningJpaRepository : JpaRepository<RunningEntity, Long> {
    fun findAllByUserIdAndStartedAtGreaterThanEqualAndStartedAtLessThanAndDeletedAtIsNull(
        userId: Long,
        monthStart: LocalDateTime,
        monthEnd: LocalDateTime,
    ): List<RunningEntity>

    fun findTopByUserIdAndDeletedAtIsNullOrderByFinishedAtDesc(userId: Long): RunningEntity?

    fun findAllByUserIdAndDeletedAtIsNull(userId: Long): List<RunningEntity>

    fun findByIdAndUserIdAndDeletedAtIsNull(
        id: Long,
        userId: Long,
    ): RunningEntity?
}
