package tamago.server.core.monster

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.image.ImageFileConstructor
import tamago.server.core.common.image.ImagePrefix
import tamago.server.core.common.image.ImageProcessor
import tamago.server.core.common.vo.MonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.application.service.MonsterCommandService
import tamago.server.core.monster.application.service.MonsterQueryService
import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.inbound.query.CreateMonsterAssetQueryDto
import tamago.server.core.monster.domain.port.inbound.query.InitMonsterQueryDto
import tamago.server.core.monster.domain.port.inbound.query.MonsterAssetBundleQueryDto
import tamago.server.core.monster.domain.port.inbound.query.MonsterDexQueryDto
import tamago.server.core.monster.domain.port.inbound.query.UploadMonsterAssetQueryDto

@Component
class MonsterFacade(
    private val monsterCommandService: MonsterCommandService,
    private val monsterQueryService: MonsterQueryService,
    private val imageProcessor: ImageProcessor,
    private val imageFileConstructor: ImageFileConstructor,
) {
    @Transactional(readOnly = true)
    fun getMonsterDex(userId: UserId): List<MonsterDexQueryDto> {
        // 1단계 몬스터와 해금 조건 함께 조회
        val firstStageMonsters = monsterQueryService.getAllFirstStageWithUnlockPolicies()
        // 내가 소유한 몬스터 조회
        val ownedMonsters = monsterQueryService.getOwnedMonstersByUserId(userId)

        // 소유 몬스터의 진화 체인을 역추적하여 1단계 몬스터 ID로 매핑
        val ownedByRootId = mutableMapOf<MonsterId, Pair<OwnedMonster, Monster>>()
        for (owned in ownedMonsters) {
            val chain = monsterQueryService.getEvolutionChain(owned.monsterId)
            val root = chain.first()
            val current = chain.first { it.id == owned.monsterId }
            ownedByRootId[root.id!!] = owned to current
        }

        return firstStageMonsters.map { baseMonster ->
            val entry = ownedByRootId[baseMonster.id]
            MonsterDexQueryDto.of(
                baseMonster = baseMonster,
                currentMonster = entry?.second,
                ownedMonster = entry?.first,
            )
        }
    }

    @Transactional
    fun initRandomMonster(userId: UserId): InitMonsterQueryDto {
        val monster = monsterQueryService.getRandomFirstStageMonster()
        val ownedMonster = monsterCommandService.initMonster(userId, monster.id!!)

        return InitMonsterQueryDto(
            ownedMonsterId = ownedMonster.id!!.value,
            monsterId = monster.id.value,
            nickname = monster.nickname,
            status = ownedMonster.status!!.name,
        )
    }

    @Transactional
    fun createMonsterAssetUploadUrl(
        monsterId: MonsterId,
        assetType: AssetType,
    ): CreateMonsterAssetQueryDto {
        // 특정 몬스터에 이미 해당 타입의 에셋이 존재하는지 확인하고 존재하면 예외 처리
        monsterQueryService.checkMonsterAssetNotExists(monsterId, assetType)

        // 이미지 파일 경로 생성
        val imageFilePath = imageFileConstructor.imageFilePath(ImagePrefix.MONSTER.value, monsterId.value)

        // 랜덤한 assetName 생성하고 presigned upload URL 생성
        val generatedUrl =
            imageProcessor.createUploadUrl(
                prefix = ImagePrefix.MONSTER.value,
                prefixId = monsterId.value,
                contentType = assetType.contentType,
                extension = assetType.extension,
            )

        val assetKey = "$imageFilePath/${generatedUrl.fileName}"

        monsterCommandService.createMonsterAsset(monsterId, assetType, assetKey, generatedUrl.fileName)

        return CreateMonsterAssetQueryDto(
            uploadUrl = generatedUrl.uploadUrl,
            previewUrl = generatedUrl.previewUrl,
            assetKey = assetKey,
        )
    }

    @Transactional
    fun uploadMonsterAsset(
        monsterId: MonsterId,
        assetType: AssetType,
        fileBytes: ByteArray,
    ): UploadMonsterAssetQueryDto {
        // 특정 몬스터에 이미 해당 타입의 에셋이 존재하는지 확인하고 존재하면 예외 처리
        monsterQueryService.checkMonsterAssetNotExists(monsterId, assetType)

        // 이미지 파일 경로와 이름 생성
        val filePath = imageFileConstructor.imageFilePath(ImagePrefix.MONSTER.value, monsterId.value)
        val fileName = imageFileConstructor.imageFileName(assetType.extension)
        val assetKey = "$filePath/$fileName"

        monsterCommandService.createMonsterAsset(monsterId, assetType, assetKey, fileName)

        val uploaded =
            imageProcessor.uploadFile(
                prefix = ImagePrefix.MONSTER.value,
                prefixId = monsterId.value,
                contentType = assetType.contentType,
                extension = assetType.extension,
                fileBytes = fileBytes,
                fileName = fileName,
            )

        return UploadMonsterAssetQueryDto(previewUrl = uploaded.previewUrl, assetKey = assetKey)
    }

    @Transactional(readOnly = true)
    fun getAllMonsterAssetBundles(): List<MonsterAssetBundleQueryDto> {
        val allAssets = monsterQueryService.getAllMonsterAssets()

        return allAssets
            .filter { it.assetName != null && it.assetType != null }
            .groupBy { it.monsterId }
            .map { (monsterId, assets) ->
                val assetList =
                    assets.map { asset ->
                        MonsterAssetBundleQueryDto.AssetDetail(
                            assetType = asset.assetType!!.name,
                            url =
                                imageProcessor
                                    .getImageUrl(
                                        prefix = ImagePrefix.MONSTER.value,
                                        prefixId = monsterId.value,
                                        fileName = asset.assetName,
                                    ).firstOrNull()
                                    ?.url
                                    .orEmpty(),
                            lastModifiedAt = asset.updatedAt!!,
                        )
                    }
                MonsterAssetBundleQueryDto(
                    monsterId = monsterId.value,
                    assets = assetList,
                )
            }
    }

    @Transactional
    fun deleteMonsterAsset(
        monsterId: MonsterId,
        assetType: AssetType,
    ) {
        val asset = monsterQueryService.getMonsterAsset(monsterId, assetType)

        imageProcessor.deleteFile(
            prefix = ImagePrefix.MONSTER.value,
            prefixId = monsterId.value,
            fileName = asset.assetName!!,
        )

        monsterCommandService.hardDeleteMonsterAsset(asset)
    }

    @Transactional
    fun createOwnedMonster(
        monsterId: MonsterId,
        userId: UserId,
    ) {
        monsterQueryService.get(monsterId)
        monsterCommandService.ownMonster(monsterId, userId)
    }
}
