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
import tamago.server.core.monster.domain.vo.MonsterId

@Component
class MonsterFacade(
    private val monsterCommandUseCase: MonsterCommandUseCase,
    private val monsterQueryUseCase: MonsterQueryUseCase,
    private val imageProcessor: ImageProcessor,
    private val imageFileConstructor: ImageFileConstructor,
) {
    @Transactional
    fun initRandomMonster(userId: UserId): InitMonsterQueryDto {
        val monster = monsterQueryUseCase.getRandomFirstStageMonster()
        val ownedMonster = monsterCommandUseCase.initMonster(userId, monster.id!!)

        return InitMonsterQueryDto(
            ownedMonsterId = ownedMonster.id!!.value,
            monsterId = monster.id!!.value,
            nickname = monster.nickname,
            status = ownedMonster.status!!.name,
        )
    }

    @Transactional
    fun createMonsterAssetUploadUrl(monsterId: MonsterId, assetType: AssetType): CreateMonsterAssetQueryDto {
        monsterQueryUseCase.checkMonsterAssetNotExists(monsterId, assetType)

        val assetKey = imageFileConstructor.imageFilePath(ImagePrefix.MONSTER.value, monsterId.value)

        val generatedUrl = imageProcessor.createUploadUrl(
            userId = 0L,
            prefix = ImagePrefix.MONSTER.value,
            prefixId = monsterId.value,
        )

        monsterCommandUseCase.createMonsterAsset(monsterId, assetType, assetKey)

        return CreateMonsterAssetQueryDto(
            uploadUrl = generatedUrl.uploadUrl,
            previewUrl = generatedUrl.previewUrl,
            assetKey = assetKey,
        )
    }
}
