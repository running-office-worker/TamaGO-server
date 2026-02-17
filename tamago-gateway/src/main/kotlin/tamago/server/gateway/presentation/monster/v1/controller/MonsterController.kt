package tamago.server.gateway.presentation.monster.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tamago.server.core.monster.MonsterFacade
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.request.InitMonsterAssetRequest
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

    @Operation(summary = "해금된 몬스터 목록 조회", description = "해금된 몬스터(알) 목록을 조회합니다.")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/unlocked")
    fun getUnlockedMonsters(
        @CurrentUser user: User,
    ): CustomResponse<List<UnlockedMonsterResponse>> {
        val unlockedMonsters = monsterQueryUseCase.getUnlockedMonsters(user.id!!)
        return CustomResponse.ok(unlockedMonsters.map { UnlockedMonsterResponse.from(it) })
    }

    @Operation(summary = "해금된 몬스터 소유", description = "해금된 몬스터를 소유 상태로 변경합니다.")
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/api/v1/monsters/unlocked/{ownedMonsterId}")
    fun ownMonster(
        @CurrentUser user: User,
        @PathVariable ownedMonsterId: Long,
    ): CustomResponse<Void> {
        monsterFacade.ownMonster(OwnedMonsterId(ownedMonsterId), user.id!!)
        return CustomResponse.ok()
    }

    @Operation(summary = "전체 몬스터 에셋 조회", description = "모든 몬스터의 에셋(PNG, GIF, LOTTIE) Presigned URL을 조회합니다.")
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
}
