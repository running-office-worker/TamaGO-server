package tamago.server.gateway.presentation.monster.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.request.InitMonsterAssetRequest
import tamago.server.gateway.presentation.monster.v1.response.InitMonsterAssetResponse
import tamago.server.gateway.presentation.monster.v1.response.InitMonsterResponse

@Tag(name = "\uD83E\uDDEA Monster API", description = "관리자용 몬스터 제어 API (local, dev 환경에서만 사용 가능)")
interface InitMonsterApi {
    @Operation(summary = "랜덤 1단계 몬스터 초기화", description = "인증된 유저에게 랜덤 1단계 몬스터를 소유시킵니다.")
    fun initRandomMonster(user: User): CustomResponse<InitMonsterResponse>

    @Operation(
        summary = "몬스터 에셋 업로드 URL 생성",
        description = "몬스터 에셋이 존재하지 않으면 S3 업로드용 Presigned URL을 생성하고 에셋 정보를 저장합니다.",
    )
    fun createMonsterAssetUploadUrl(request: InitMonsterAssetRequest): CustomResponse<InitMonsterAssetResponse>
}
