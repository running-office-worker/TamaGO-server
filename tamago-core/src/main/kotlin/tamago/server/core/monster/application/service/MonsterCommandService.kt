package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort

@Service
class MonsterCommandService(
    private val monsterPersistencePort: MonsterPersistencePort,
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
) : MonsterCommandUseCase {

    override fun initRandomMonster(userId: UserId): OwnedMonster {
        val firstStageMonsters = monsterPersistencePort.findAllByPreviousMonsterIdIsNull()
        val selected = firstStageMonsters.random()

        val ownedMonster = OwnedMonster.create(
            monsterId = selected.id!!,
            userId = userId,
            havingXp = 0,
            status = OwnedMonsterStatus.OWNED,
        )

        return ownedMonsterPersistencePort.save(ownedMonster)
    }
}
