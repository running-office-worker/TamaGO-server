package tamago.server.core.monster.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.infrastructure.entity.MonsterEntity

interface MonsterJpaRepository : JpaRepository<MonsterEntity, Long> {
    fun findAllByPreviousMonsterIsNull(): List<MonsterEntity>
}
