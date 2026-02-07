package tamago.server.core.monster.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.monster.infrastructure.mapper.OwnedMonsterMapper

@Repository
class OwnedMonsterPersistenceAdapter(
    private val ownedMonsterJpaRepository: OwnedMonsterJpaRepository,
) : OwnedMonsterPersistencePort {

    override fun findById(id: OwnedMonsterId): OwnedMonster? =
        OwnedMonsterMapper.toDomain(ownedMonsterJpaRepository.findById(id.value).orElse(null))
}
