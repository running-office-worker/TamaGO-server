package tamago.server.gateway.presentation.auth.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.gateway.presentation.auth.v1.request.AppleLoginRequest
import tamago.server.gateway.presentation.auth.v1.request.KakaoLoginRequest
import tamago.server.gateway.presentation.auth.v1.response.SocialLoginResponse
import tamago.server.gateway.response.CustomResponse

@Tag(name = "Auth API", description = "인증 관련 API")
interface AuthApi {
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "로그인 성공"),
            ApiResponse(responseCode = "400", description = "OAUTH_4000: 소셜 로그인 사용자를 검증하는데 실패했습니다. <br>" +
                    "USER_4000: 유저를 저장하는 도중 오류가 발생했습니다."),
            ApiResponse(responseCode = "404", description = "USER_4040: 유저를 찾을 수 없습니다."),
        ]
    )
    @Operation(summary = "카카오 소셜 로그인", description = "access token을 받아 카카오 소셜 로그인을 처리합니다.")
    fun socialKakaoLogin(request: KakaoLoginRequest): CustomResponse<SocialLoginResponse>

    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "로그인 성공"),
            ApiResponse(responseCode = "400", description = "OAUTH_4000: 소셜 로그인 사용자를 검증하는데 실패했습니다. <br>" +
                    "USER_4000: 유저를 저장하는 도중 오류가 발생했습니다."),
            ApiResponse(responseCode = "404", description = "USER_4040: 유저를 찾을 수 없습니다."),
        ]
    )
    @Operation(summary = "애플 소셜 로그인", description = "애플 소셜 로그인을 처리합니다.")
    fun socialAppleLogin(request: AppleLoginRequest): CustomResponse<SocialLoginResponse>
}