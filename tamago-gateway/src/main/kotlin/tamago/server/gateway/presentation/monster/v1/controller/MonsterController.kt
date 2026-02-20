package tamago.server.gateway.presentation.monster.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import tamago.server.core.monster.MonsterFacade
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.domain.enum.AssetType
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.request.InitMonsterAssetRequest
import tamago.server.gateway.presentation.monster.v1.request.OwnMonsterRequest
import tamago.server.gateway.presentation.monster.v1.response.*

@Tag(name = "Monster API", description = "몬스터 도감 API")
@RestController
class MonsterController(
    private val monsterFacade: MonsterFacade,
    private val monsterQueryUseCase: MonsterQueryUseCase,
) {

    @Operation(summary = "전체 몬스터 도감 조회", description = "전체 몬스터 캐릭터 도감 정보를 조회합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/dex")
    fun getMonsterDex(
        @CurrentUser user: User,
    ): CustomResponse<List<MonsterDexResponse>> {
        val result = monsterFacade.getMonsterDex(user.id!!)
        return CustomResponse.ok(result.map { MonsterDexResponse.from(it) })
    }

    @Operation(summary = "소유 몬스터 ID 매핑 조회", description = "소유한 몬스터의 ownedMonsterId, monsterId 매핑 배열을 반환합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/owned")
    fun getOwnedMonsterMappings(
        @CurrentUser user: User,
    ): CustomResponse<List<OwnedMonsterMappingResponse>> {
        val result = monsterQueryUseCase.getOwnedMonsterMappings(user.id!!)
        return CustomResponse.ok(result.map { OwnedMonsterMappingResponse.from(it) })
    }

    @Operation(summary = "\uD83E\uDDEA 몬스터 소유", description = "monsterId로 OwnedMonster를 생성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/v1/monsters/owned")
    fun createOwnedMonster(
        @CurrentUser user: User,
        @RequestBody request: OwnMonsterRequest,
    ): CustomResponse<Void> {
        monsterFacade.createOwnedMonster(MonsterId(request.monsterId), user.id!!)
        return CustomResponse.created()
    }

    @Operation(
        summary = "전체 몬스터 에셋 조회",
        description = "모든 몬스터의 에셋(SVG, PNG, GIF, LOTTIE) Presigned URL을 조회합니다.",
    )
    @GetMapping("/api/v1/monsters/assets")
    fun getAllMonsterAssets(): CustomResponse<List<MonsterAssetBundleResponse>> {
        val result = monsterFacade.getAllMonsterAssetBundles()
        return CustomResponse.ok(result.map { MonsterAssetBundleResponse.from(it) })
    }

    @Operation(summary = "몬스터 에셋 변경 여부 확인", description = "마지막 로그인 이후 몬스터 에셋이 변경되었는지 확인합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/assets/check-updates")
    fun checkMonsterAssetUpdates(
        @CurrentUser user: User,
    ): CustomResponse<MonsterAssetUpdateCheckResponse> {
        val hasUpdates = monsterQueryUseCase.hasMonsterAssetUpdates(user.lastLoginAt)
        return CustomResponse.ok(MonsterAssetUpdateCheckResponse(hasUpdates))
    }

    @Operation(summary = "\uD83E\uDDEA 랜덤 1단계 몬스터 초기화", description = "인증된 유저에게 랜덤 1단계 몬스터를 소유시킵니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/monsters/init")
    fun initRandomMonster(
        @CurrentUser user: User,
    ): CustomResponse<InitMonsterResponse> {
        val result = monsterFacade.initRandomMonster(user.id!!)

        return CustomResponse.created(
            InitMonsterResponse(
                ownedMonsterId = result.ownedMonsterId,
                monsterId = result.monsterId,
                nickname = result.nickname,
                status = result.status,
            ),
        )
    }

    @Operation(
        summary = "\uD83E\uDDEA 몬스터 에셋 업로드 URL 생성",
        description = "몬스터 에셋이 존재하지 않으면 S3 업로드용 Presigned URL을 생성하고 에셋 정보를 저장합니다.",
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/monsters/upload")
    fun createMonsterAssetUploadUrl(
        @RequestBody request: InitMonsterAssetRequest,
    ): CustomResponse<InitMonsterAssetResponse> {
        val result = monsterFacade.createMonsterAssetUploadUrl(
            monsterId = MonsterId(request.monsterId),
            assetType = request.assetType,
        )

        return CustomResponse.created(
            InitMonsterAssetResponse(
                uploadUrl = result.uploadUrl,
                previewUrl = result.previewUrl,
                assetKey = result.assetKey,
            ),
        )
    }

    @Operation(summary = "\uD83E\uDDEA 몬스터 에셋 직접 업로드", description = "파일을 서버를 통해 직접 S3에 업로드합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/monsters/{monsterId}/assets", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadMonsterAsset(
        @PathVariable monsterId: Long,
        @RequestParam assetType: AssetType,
        @RequestPart("file") file: MultipartFile,
    ): CustomResponse<UploadMonsterAssetResponse> {
        val result = monsterFacade.uploadMonsterAsset(MonsterId(monsterId), assetType, file.bytes)
        return CustomResponse.created(UploadMonsterAssetResponse(result.previewUrl, result.assetKey))
    }
}
