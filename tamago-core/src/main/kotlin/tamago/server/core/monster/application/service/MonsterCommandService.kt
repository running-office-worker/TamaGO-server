package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId

@Service
class MonsterCommandService(
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
) : MonsterCommandUseCase {

    override fun initMonster(userId: UserId, monsterId: MonsterId): OwnedMonster {
        val ownedMonster = OwnedMonster.create(
            monsterId = monsterId,
            userId = userId,
            havingXp = 0,
            status = OwnedMonsterStatus.OWNED,
        )

        return ownedMonsterPersistencePort.save(ownedMonster)
    }
}
