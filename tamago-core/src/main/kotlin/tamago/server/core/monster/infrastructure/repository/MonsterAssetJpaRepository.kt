package tamago.server.core.monster.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tamago.server.core.monster.infrastructure.entity.MonsterAssetEntity

interface MonsterAssetJpaRepository : JpaRepository<MonsterAssetEntity, Long>
