package tamago.server.core.monster.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.infrastructure.entity.OwnedMonsterEntity

interface OwnedMonsterJpaRepository : JpaRepository<OwnedMonsterEntity, Long> {
    fun findAllByUserId(userId: Long): List<OwnedMonsterEntity>
    fun findAllByUserIdAndStatusAndDeletedAtIsNull(userId: Long, status: OwnedMonsterStatus): List<OwnedMonsterEntity>
}
