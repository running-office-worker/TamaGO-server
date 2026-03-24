package tamago.server.storage.monster.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.monster.entity.MonsterAssetEntity
import java.time.LocalDateTime

interface MonsterAssetJpaRepository : JpaRepository<MonsterAssetEntity, Long> {
    fun existsByUpdatedAtAfterAndDeletedAtIsNull(since: LocalDateTime): Boolean

    fun findAllByDeletedAtIsNull(): List<MonsterAssetEntity>
}
