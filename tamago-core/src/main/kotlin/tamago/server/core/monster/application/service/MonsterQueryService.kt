package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.image.ImagePrefix
import tamago.server.core.common.image.ImageProcessor
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.application.exception.MonsterAssetAlreadyExistsException
import tamago.server.core.monster.application.exception.MonsterNotFoundException
import tamago.server.core.monster.application.exception.OwnedMonsterNotFoundException
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.enum.OwnedMonsterStatus
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import java.time.LocalDateTime

@Service
class MonsterQueryService(
    private val monsterPersistencePort: MonsterPersistencePort,
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
    private val monsterAssetPersistencePort: MonsterAssetPersistencePort,
    private val imageProcessor: ImageProcessor,
) : MonsterQueryUseCase {

    fun get(id: MonsterId): Monster =
        monsterPersistencePort.findById(id)
            ?: throw MonsterNotFoundException()

    fun getAllWithUnlockPolicies(): List<Monster> =
        monsterPersistencePort.findAllWithUnlockPolicies()

    fun getAllFirstStageWithUnlockPolicies(): List<Monster> =
        monsterPersistencePort.findAllFirstStageWithUnlockPolicies()

    override fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster =
        ownedMonsterPersistencePort.findById(id)
            ?: throw OwnedMonsterNotFoundException()

    fun getOwnedMonstersByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterPersistencePort.findAllByUserId(userId)

    fun getMonsterAssetsByMonsterIds(monsterIds: List<MonsterId>, assetType: AssetType): List<MonsterAsset> =
        monsterAssetPersistencePort.findAllByMonsterIdsAndAssetType(monsterIds, assetType)

    fun getAllMonsterAssets(): List<MonsterAsset> =
        monsterAssetPersistencePort.findAll()

    override fun hasMonsterAssetUpdates(lastLoginAt: LocalDateTime?): Boolean =
        lastLoginAt?.let { monsterAssetPersistencePort.existsByUpdatedAtAfter(it) } ?: true

    override fun getEvolutionChain(monsterId: MonsterId): List<Monster> {
        val current = monsterPersistencePort.findById(monsterId) ?: throw MonsterNotFoundException()

        // 이전 진화 단계 몬스터들을 역순으로 수집
        val previousChain = mutableListOf<Monster>()
        var prevId: MonsterId? = current.previousMonsterId
        while (prevId != null) {
            val monster = monsterPersistencePort.findById(prevId) ?: break
            previousChain.add(monster)
            prevId = monster.previousMonsterId
        }

        // 다음 진화 단계 몬스터들을 순서대로 수집
        val nextChain = mutableListOf<Monster>()
        var nextId: MonsterId? = current.nextMonsterId
        while (nextId != null) {
            val monster = monsterPersistencePort.findById(nextId) ?: break
            nextChain.add(monster)
            nextId = monster.nextMonsterId
        }

        // 이전 단계 몬스터들 + 현재 몬스터 + 다음 단계 몬스터들 합치기
        return previousChain.reversed() + current + nextChain
    }

    fun getRandomFirstStageMonster(): Monster {
        val firstStageMonsters = monsterPersistencePort.findAllByPreviousMonsterIdIsNull()
        if (firstStageMonsters.isEmpty()) throw MonsterNotFoundException()
        return firstStageMonsters.random()
    }

    fun checkMonsterAssetNotExists(monsterId: MonsterId, assetType: AssetType) {
        if (monsterAssetPersistencePort.existsByMonsterIdAndAssetType(monsterId, assetType)) {
            throw MonsterAssetAlreadyExistsException()
        }
    }

    override fun getUnlockedMonsters(userId: UserId): List<OwnedMonster> =
        ownedMonsterPersistencePort.findAllByUserIdAndStatus(userId, OwnedMonsterStatus.UNLOCKED)

}
