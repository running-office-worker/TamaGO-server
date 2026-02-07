package tamago.server.core.running.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.running.infrastructure.entity.RunningEntity

interface RunningJpaRepository : JpaRepository<RunningEntity, Long>
