package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
import tamago.server.core.monster.application.exception.OwnedMonsterNotFoundException
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort

@Service
class MonsterCommandService(
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
    private val monsterAssetPersistencePort: MonsterAssetPersistencePort,
) : MonsterCommandUseCase {
    fun initMonster(
        userId: UserId,
        monsterId: MonsterId,
    ): OwnedMonster {
        val ownedMonster =
            OwnedMonster.create(
                monsterId = monsterId,
                userId = userId,
                havingXp = 0,
            )

        return ownedMonsterPersistencePort.save(ownedMonster)
    }

    fun createMonsterAsset(
        monsterId: MonsterId,
        assetType: AssetType,
        assetKey: String,
        assetName: String,
    ): MonsterAsset {
        val monsterAsset =
            MonsterAsset.create(
                monsterId = monsterId,
                assetKey = assetKey,
                assetName = assetName,
                assetType = assetType,
            )

        return monsterAssetPersistencePort.save(monsterAsset)
    }

    override fun addEarnedXp(
        ownedMonsterId: OwnedMonsterId,
        xp: Int,
    ) {
        val ownedMonster =
            ownedMonsterPersistencePort.findById(ownedMonsterId)
                ?: throw OwnedMonsterNotFoundException()
        ownedMonster.addXp(xp)
        ownedMonsterPersistencePort.save(ownedMonster)
    }

    fun evolve(
        ownedMonster: OwnedMonster,
        nextMonsterId: MonsterId,
    ) {
        ownedMonster.evolve(nextMonsterId)
        ownedMonsterPersistencePort.save(ownedMonster)
    }

    fun delete(ownedMonster: OwnedMonster) {
        ownedMonster.delete()
        ownedMonsterPersistencePort.save(ownedMonster)
    }

    fun ownMonster(
        monsterId: MonsterId,
        userId: UserId,
    ): OwnedMonster {
        val ownedMonster =
            OwnedMonster.create(
                monsterId = monsterId,
                userId = userId,
                havingXp = 0,
            )
        return ownedMonsterPersistencePort.save(ownedMonster)
    }
}
