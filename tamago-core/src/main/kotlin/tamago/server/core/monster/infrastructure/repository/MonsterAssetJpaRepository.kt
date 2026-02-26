package tamago.server.core.monster.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.infrastructure.entity.MonsterAssetEntity
import java.time.LocalDateTime

interface MonsterAssetJpaRepository : JpaRepository<MonsterAssetEntity, Long> {
    fun existsByUpdatedAtAfterAndDeletedAtIsNull(since: LocalDateTime): Boolean

    fun findAllByDeletedAtIsNull(): List<MonsterAssetEntity>
}
