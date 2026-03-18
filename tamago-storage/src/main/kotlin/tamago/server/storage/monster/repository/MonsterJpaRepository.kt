package tamago.server.storage.monster.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.domain.enum.MonsterType
import tamago.server.storage.monster.entity.MonsterEntity

interface MonsterJpaRepository : JpaRepository<MonsterEntity, Long> {
    fun findAllByPreviousMonsterIsNull(): List<MonsterEntity>

    fun findByMonsterType(monsterType: MonsterType): MonsterEntity?
}
