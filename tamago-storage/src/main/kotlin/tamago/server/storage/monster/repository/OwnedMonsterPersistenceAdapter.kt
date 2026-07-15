package tamago.server.storage.monster.repository

import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.storage.monster.mapper.OwnedMonsterMapper
import java.time.LocalDateTime

@Repository
class OwnedMonsterPersistenceAdapter(
    private val ownedMonsterJpaRepository: OwnedMonsterJpaRepository,
    private val monsterJpaRepository: MonsterJpaRepository,
) : OwnedMonsterPersistencePort {
    override fun findById(id: OwnedMonsterId): OwnedMonster? =
        OwnedMonsterMapper.toDomain(ownedMonsterJpaRepository.findById(id.value).orElse(null))

    override fun findAllByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterJpaRepository
            .findAllByUserIdAndDeletedAtIsNull(userId.value)
            .mapNotNull { OwnedMonsterMapper.toDomain(it) }

    override fun findAllByUserIdAndCreatedAfter(
        userId: UserId,
        after: LocalDateTime,
    ): List<OwnedMonster> =
        ownedMonsterJpaRepository
            .findAllByUserIdAndCreatedAtAfterAndDeletedAtIsNull(userId.value, after)
            .mapNotNull { OwnedMonsterMapper.toDomain(it) }

    override fun save(ownedMonster: OwnedMonster): OwnedMonster {
        val monsterEntity = monsterJpaRepository.findById(ownedMonster.monsterId.value).orElseThrow()
        val entity = OwnedMonsterMapper.toEntity(ownedMonster, monsterEntity)
        val saved = ownedMonsterJpaRepository.save(entity)
        return OwnedMonsterMapper.toDomain(saved)!!
    }

    override fun findAllDeletedByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterJpaRepository
            .findAllByUserIdAndDeletedAtIsNotNull(userId.value)
            .mapNotNull { OwnedMonsterMapper.toDomain(it) }

    override fun hardDeleteAllByUserId(userId: UserId) {
        ownedMonsterJpaRepository.deleteAllByUserId(userId.value)
    }
}
