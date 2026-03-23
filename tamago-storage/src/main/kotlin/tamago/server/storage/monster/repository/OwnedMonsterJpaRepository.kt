package tamago.server.storage.monster.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.monster.entity.OwnedMonsterEntity
import java.time.LocalDateTime

interface OwnedMonsterJpaRepository : JpaRepository<OwnedMonsterEntity, Long> {
    fun findAllByUserIdAndDeletedAtIsNull(userId: Long): List<OwnedMonsterEntity>

    fun findAllByUserIdAndCreatedAtAfterAndDeletedAtIsNull(
        userId: Long,
        after: LocalDateTime,
    ): List<OwnedMonsterEntity>

    fun findAllByUserIdAndDeletedAtIsNotNull(userId: Long): List<OwnedMonsterEntity>
}
