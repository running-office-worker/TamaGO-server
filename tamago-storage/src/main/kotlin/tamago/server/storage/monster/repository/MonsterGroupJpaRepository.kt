package tamago.server.storage.monster.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.storage.monster.entity.MonsterGroupEntity

interface MonsterGroupJpaRepository : JpaRepository<MonsterGroupEntity, Long> {
    fun findByCode(code: String): MonsterGroupEntity?
}
