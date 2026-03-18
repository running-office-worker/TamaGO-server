package tamago.server.storage.running.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.running.entity.RunningRouteEntity

interface RunningRouteJpaRepository : JpaRepository<RunningRouteEntity, Long> {
    fun findByRunningIdAndDeletedAtIsNull(runningId: Long): RunningRouteEntity?
}
