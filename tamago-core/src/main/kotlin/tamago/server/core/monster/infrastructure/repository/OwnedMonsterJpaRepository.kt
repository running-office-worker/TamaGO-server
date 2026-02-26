package tamago.server.core.monster.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.infrastructure.entity.OwnedMonsterEntity
import java.time.LocalDateTime

interface OwnedMonsterJpaRepository : JpaRepository<OwnedMonsterEntity, Long> {
    fun findAllByUserIdAndDeletedAtIsNull(userId: Long): List<OwnedMonsterEntity>

    fun findAllByUserIdAndCreatedAtAfterAndDeletedAtIsNull(
        userId: Long,
        after: LocalDateTime,
    ): List<OwnedMonsterEntity>
}
