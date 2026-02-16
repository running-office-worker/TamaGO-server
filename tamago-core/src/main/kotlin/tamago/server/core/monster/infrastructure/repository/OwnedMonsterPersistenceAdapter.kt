package tamago.server.core.monster.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.monster.infrastructure.mapper.OwnedMonsterMapper

@Repository
class OwnedMonsterPersistenceAdapter(
    private val ownedMonsterJpaRepository: OwnedMonsterJpaRepository,
    private val monsterJpaRepository: MonsterJpaRepository,
) : OwnedMonsterPersistencePort {

    override fun findById(id: OwnedMonsterId): OwnedMonster? =
        OwnedMonsterMapper.toDomain(ownedMonsterJpaRepository.findById(id.value).orElse(null))

    override fun findAllByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterJpaRepository.findAllByUserId(userId.value).mapNotNull { OwnedMonsterMapper.toDomain(it) }

    override fun findAllByUserIdAndStatus(userId: UserId, status: OwnedMonsterStatus): List<OwnedMonster> =
        ownedMonsterJpaRepository.findAllByUserIdAndStatusAndDeletedAtIsNull(userId.value, status)
            .mapNotNull { OwnedMonsterMapper.toDomain(it) }

    override fun save(ownedMonster: OwnedMonster): OwnedMonster {
        val monsterEntity = monsterJpaRepository.findById(ownedMonster.monsterId.value).orElseThrow()
        val entity = OwnedMonsterMapper.toEntity(ownedMonster, monsterEntity)
        val saved = ownedMonsterJpaRepository.save(entity)
        return OwnedMonsterMapper.toDomain(saved)!!
    }
}
