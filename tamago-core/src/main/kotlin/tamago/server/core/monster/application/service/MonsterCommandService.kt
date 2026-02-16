package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId

@Service
class MonsterCommandService(
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
    private val monsterAssetPersistencePort: MonsterAssetPersistencePort,
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

    override fun createMonsterAsset(monsterId: MonsterId, assetType: AssetType, assetKey: String): MonsterAsset {
        val monsterAsset = MonsterAsset.create(
            monsterId = monsterId,
            assetKey = assetKey,
            assetType = assetType,
        )

        return monsterAssetPersistencePort.save(monsterAsset)
    }

    override fun addEarnedXp(ownedMonster: OwnedMonster, xp: Int): OwnedMonster {
        val updated = ownedMonster.addXp(xp)
        return ownedMonsterPersistencePort.save(updated)
    }
}
