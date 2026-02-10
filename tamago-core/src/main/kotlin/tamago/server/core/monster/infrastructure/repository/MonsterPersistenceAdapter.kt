package tamago.server.core.monster.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.infrastructure.mapper.MonsterMapper

@Repository
class MonsterPersistenceAdapter(
    private val monsterJpaRepository: MonsterJpaRepository,
) : MonsterPersistencePort {

    override fun findById(id: MonsterId): Monster? =
        MonsterMapper.toDomain(monsterJpaRepository.findById(id.value).orElse(null))

    override fun findAllByPreviousMonsterIdIsNull(): List<Monster> =
        monsterJpaRepository.findAllByPreviousMonsterIsNull().mapNotNull { MonsterMapper.toDomain(it) }
}
