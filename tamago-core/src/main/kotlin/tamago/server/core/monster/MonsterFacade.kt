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
import tamago.server.core.monster.application.exception.MonsterAccessDeniedException
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
        // 특정 몬스터에 이미 해당 타입의 에셋이 존재하는지 확인하고 존재하면 예외 처리
        monsterQueryService.checkMonsterAssetNotExists(monsterId, assetType)

        // 이미지 파일 경로 생성
        val imageFilePath = imageFileConstructor.imageFilePath(ImagePrefix.MONSTER.value, monsterId.value)

        // 랜덤한 assetName 생성하고 presigned upload URL 생성
        val generatedUrl = imageProcessor.createUploadUrl(
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
    fun ownMonster(ownedMonsterId: OwnedMonsterId, ownerId: UserId) {
        val ownedMonster = monsterQueryService.getOwnedMonster(ownedMonsterId)
        if (ownedMonster.userId != ownerId) {
            throw MonsterAccessDeniedException()
        }
        monsterCommandService.ownMonster(ownedMonster)
    }
}
