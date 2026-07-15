package tamago.server.core.monster.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterQuery
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.application.exception.MonsterAssetAlreadyExistsException
import tamago.server.core.monster.application.exception.MonsterAssetNotFoundException
import tamago.server.core.monster.application.exception.MonsterNotFoundException
import tamago.server.core.monster.application.exception.OwnedMonsterNotFoundException
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.MonsterAsset
import tamago.server.core.monster.domain.aggregate.MonsterGroupAsset
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.outbound.MonsterAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.MonsterGroupAssetPersistencePort
import tamago.server.core.monster.domain.port.outbound.MonsterPersistencePort
import tamago.server.core.monster.domain.port.outbound.OwnedMonsterPersistencePort
import tamago.server.core.monster.domain.vo.BackgroundAssetMetadata
import java.time.LocalDateTime

@Service
class MonsterQueryService(
    private val monsterPersistencePort: MonsterPersistencePort,
    private val ownedMonsterPersistencePort: OwnedMonsterPersistencePort,
    private val monsterAssetPersistencePort: MonsterAssetPersistencePort,
    private val monsterGroupAssetPersistencePort: MonsterGroupAssetPersistencePort,
) : MonsterQueryUseCase {
    fun get(id: MonsterId): Monster =
        monsterPersistencePort.findById(id)
            ?: throw MonsterNotFoundException()

    fun getAllWithUnlockPolicies(): List<Monster> = monsterPersistencePort.findAllWithUnlockPolicies()

    fun getAllFirstStageWithUnlockPolicies(): List<Monster> =
        monsterPersistencePort.findAllFirstStageWithUnlockPolicies()

    fun getDefaultMonster(): Monster? = monsterPersistencePort.findDefaultMonster()

    override fun getOwnedMonster(id: OwnedMonsterId): OwnedMonster =
        ownedMonsterPersistencePort.findById(id)
            ?: throw OwnedMonsterNotFoundException()

    fun getOwnedMonstersByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterPersistencePort.findAllByUserId(userId)

    fun getDeletedOwnedMonstersByUserId(userId: UserId): List<OwnedMonster> =
        ownedMonsterPersistencePort.findAllDeletedByUserId(userId)

    fun getMonsterAssetsByMonsterIds(
        monsterIds: List<MonsterId>,
        assetType: AssetType,
    ): List<MonsterAsset> = monsterAssetPersistencePort.findAllByMonsterIdsAndAssetType(monsterIds, assetType)

    fun getAllMonsterAssets(): List<MonsterAsset> = monsterAssetPersistencePort.findAll()

    fun getAllMonsterGroupAssets(): List<MonsterGroupAsset> = monsterGroupAssetPersistencePort.findAll()

    override fun getRunningBackgroundAssets(
        ownedMonsterId: OwnedMonsterId,
        hour: Int,
    ): List<MonsterQuery.BackgroundAsset> {
        val ownedMonster = getOwnedMonster(ownedMonsterId)
        val monster = get(ownedMonster.monsterId)
        val monsterGroupId = requireNotNull(monster.monsterGroupId) { "monsterGroupId is required" }
        val assets =
            monsterGroupAssetPersistencePort.findAllByMonsterGroupIdAndAssetTypes(
                monsterGroupId = monsterGroupId,
                assetTypes = listOf(AssetType.LBG_PNG, AssetType.RBG_PNG),
            )

        val matchedAssets =
            assets
                .filter { asset ->
                    asset.assetKey != null &&
                        asset.assetName != null &&
                        asset.assetType != null &&
                        (asset.metadata as? BackgroundAssetMetadata)?.containsHour(hour) == true
                }.associateBy { it.assetType }

        val selectedAssets = listOfNotNull(matchedAssets[AssetType.LBG_PNG], matchedAssets[AssetType.RBG_PNG])
        if (selectedAssets.size != 2) throw MonsterAssetNotFoundException()

        return selectedAssets.map { it.toRunningBackgroundAsset() }
    }

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

    fun getMonsterAsset(
        monsterId: MonsterId,
        assetType: AssetType,
    ): MonsterAsset =
        monsterAssetPersistencePort.findByMonsterIdAndAssetType(monsterId, assetType)
            ?: throw MonsterAssetNotFoundException()

    fun checkMonsterAssetNotExists(
        monsterId: MonsterId,
        assetType: AssetType,
    ) {
        if (monsterAssetPersistencePort.existsByMonsterIdAndAssetType(monsterId, assetType)) {
            throw MonsterAssetAlreadyExistsException()
        }
    }

    override fun getOwnedMonsterMappings(userId: UserId): List<OwnedMonster> =
        ownedMonsterPersistencePort.findAllByUserId(userId)

    override fun getOwnedMonstersAfter(
        userId: UserId,
        after: LocalDateTime,
    ): List<OwnedMonster> = ownedMonsterPersistencePort.findAllByUserIdAndCreatedAfter(userId, after)

    override fun calculateEarnedXp(
        ownedMonsterId: OwnedMonsterId,
        distance: Double,
    ): MonsterQuery.XpResult {
        val ownedMonster = getOwnedMonster(ownedMonsterId)
        val evolutionChain = getEvolutionChain(ownedMonster.monsterId)
        val earnedXp =
            evolutionChain
                .first { it.id == ownedMonster.monsterId }
                .evolutionPolicy
                ?.calculateXp(distance) ?: 0

        return MonsterQuery.XpResult(
            originXp = ownedMonster.havingXp ?: 0,
            earnedXp = earnedXp,
            evolutionStages =
                evolutionChain.mapIndexed { index, monster ->
                    MonsterQuery.XpResult.EvolutionStage(
                        stage = index + 1,
                        monsterId = monster.id!!.value,
                        evolutionXp = monster.evolutionXp,
                        current = monster.id == ownedMonster.monsterId,
                    )
                },
        )
    }

    private fun MonsterGroupAsset.toRunningBackgroundAsset(): MonsterQuery.BackgroundAsset {
        val backgroundMetadata = metadata as? BackgroundAssetMetadata

        return MonsterQuery.BackgroundAsset(
            assetType = assetType!!.name,
            fileName = assetName!!,
            assetKey = assetKey!!,
            lastModifiedAt = updatedAt!!,
            metadata =
                backgroundMetadata?.let {
                    MonsterQuery.BackgroundAsset.Metadata(
                        backgroundColor = it.backgroundColor,
                        startHour = it.startHour,
                        endHour = it.endHour,
                    )
                },
        )
    }
}
