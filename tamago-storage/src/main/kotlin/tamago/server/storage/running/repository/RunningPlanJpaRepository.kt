package tamago.server.storage.running.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.running.entity.RunningPlanEntity

interface RunningPlanJpaRepository : JpaRepository<RunningPlanEntity, Long> {
    fun findByIdAndUserIdAndDeletedAtIsNull(
        id: Long,
        userId: Long,
    ): RunningPlanEntity?
}
