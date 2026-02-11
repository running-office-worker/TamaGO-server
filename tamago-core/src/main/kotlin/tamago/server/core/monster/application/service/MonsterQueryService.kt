package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.application.exception.MonsterAssetAlreadyExistsException
import tamago.server.core.monster.application.exception.MonsterNotFoundException
import tamago.server.core.monster.application.exception.OwnedMonsterNotFoundException
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId

@Service
class MonsterQueryService(
    private val monsterPersistencePort: MonsterPersistencePort,
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
    private val monsterAssetPersistencePort: MonsterAssetPersistencePort,
) : MonsterQueryUseCase {

    override fun get(id: MonsterId): Monster =
        monsterPersistencePort.findById(id)
            ?: throw MonsterNotFoundException()

    override fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster =
        ownedMonsterPersistencePort.findById(id)
            ?: throw OwnedMonsterNotFoundException()

    override fun getEvolutionChain(monsterId: MonsterId): List<Monster> {
        val current = monsterPersistencePort.findById(monsterId) ?: throw MonsterNotFoundException()

        val previousChain = mutableListOf<Monster>()
        var prevId: MonsterId? = current.previousMonsterId
        while (prevId != null) {
            val monster = monsterPersistencePort.findById(prevId) ?: break
            previousChain.add(monster)
            prevId = monster.previousMonsterId
        }

        val nextChain = mutableListOf<Monster>()
        var nextId: MonsterId? = current.nextMonsterId
        while (nextId != null) {
            val monster = monsterPersistencePort.findById(nextId) ?: break
            nextChain.add(monster)
            nextId = monster.nextMonsterId
        }

        return previousChain.reversed() + current + nextChain
    }

    override fun getRandomFirstStageMonster(): Monster {
        val firstStageMonsters = monsterPersistencePort.findAllByPreviousMonsterIdIsNull()
        if (firstStageMonsters.isEmpty()) throw MonsterNotFoundException()
        return firstStageMonsters.random()
    }

    override fun checkMonsterAssetNotExists(monsterId: MonsterId, assetType: AssetType) {
        if (monsterAssetPersistencePort.existsByMonsterIdAndAssetType(monsterId, assetType)) {
            throw MonsterAssetAlreadyExistsException()
        }
    }
}
