package tamago.server.core.monster

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.image.ImageFileConstructor
import tamago.server.core.common.image.ImagePrefix
import tamago.server.core.common.image.ImageProcessor
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.port.inbound.query.CreateMonsterAssetQueryDto
import tamago.server.core.monster.domain.port.inbound.query.InitMonsterQueryDto
import tamago.server.core.monster.domain.port.inbound.query.MonsterDexQueryDto
import tamago.server.core.monster.application.service.MonsterCommandService
import tamago.server.core.monster.application.service.MonsterQueryService
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId

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
        val monsters = monsterQueryService.getAllFirstStageWithUnlockPolicies()
        // 내가 소유한 몬스터 조회
        val ownedMonsterMap = monsterQueryService.getOwnedMonstersByUserId(userId)
            .associateBy { it.monsterId }

        return monsters.map { monster ->
            // 내가 소유한 몬스터인지 확인
            val ownedMonster = ownedMonsterMap[monster.id]
            // 몬스터의 PNG 이미지만 조회
            val imageUrl = ownedMonster?.let { monsterQueryService.getMonsterPngUrl(monster.id!!) }

            MonsterDexQueryDto.of(monster, ownedMonster, imageUrl)
        }
    }

    @Transactional
    fun initRandomMonster(userId: UserId): InitMonsterQueryDto {
        val monster = monsterQueryService.getRandomFirstStageMonster()
        val ownedMonster = monsterCommandService.initMonster(userId, monster.id!!)

        return InitMonsterQueryDto(
            ownedMonsterId = ownedMonster.id!!.value,
            monsterId = monster.id!!.value,
            nickname = monster.nickname,
            status = ownedMonster.status!!.name,
        )
    }

    @Transactional
    fun createMonsterAssetUploadUrl(monsterId: MonsterId, assetType: AssetType): CreateMonsterAssetQueryDto {
        monsterQueryService.checkMonsterAssetNotExists(monsterId, assetType)

        val assetKey = imageFileConstructor.imageFilePath(ImagePrefix.MONSTER.value, monsterId.value)

        val generatedUrl = imageProcessor.createUploadUrl(
            prefix = ImagePrefix.MONSTER.value,
            prefixId = monsterId.value,
            contentType = assetType.contentType,
            extension = assetType.extension,
        )

        monsterCommandService.createMonsterAsset(monsterId, assetType, assetKey)

        return CreateMonsterAssetQueryDto(
            uploadUrl = generatedUrl.uploadUrl,
            previewUrl = generatedUrl.previewUrl,
            assetKey = assetKey,
        )
    }

    @Transactional
    fun ownMonster(ownedMonsterId: OwnedMonsterId) {
        val ownedMonster = monsterQueryService.getOwnedMonster(ownedMonsterId)
        monsterCommandService.ownMonster(ownedMonster)
    }
}
