package tamago.server.gateway.presentation.auth.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.gateway.presentation.auth.v1.request.AppleLoginRequest
import tamago.server.gateway.presentation.auth.v1.request.KakaoLoginRequest
import tamago.server.gateway.presentation.auth.v1.response.SocialLoginResponse
import tamago.server.gateway.response.CustomResponse

@Tag(name = "Auth API", description = "인증 관련 API")
interface AuthApi {
    @Operation(summary = "카카오 소셜 로그인", description = "access token을 받아 카카오 소셜 로그인을 처리합니다.")
    fun socialKakaoLogin(request: KakaoLoginRequest): CustomResponse<SocialLoginResponse>

    @Operation(summary = "애플 소셜 로그인", description = "애플 소셜 로그인을 처리합니다.")
    fun socialAppleLogin(request: AppleLoginRequest): CustomResponse<SocialLoginResponse>
}