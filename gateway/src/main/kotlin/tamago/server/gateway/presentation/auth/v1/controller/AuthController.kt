package tamago.server.gateway.presentation.auth.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserLoginUseCase
import tamago.server.gateway.presentation.auth.v1.api.AuthApi
import tamago.server.gateway.presentation.auth.v1.request.TokenRequest
import tamago.server.gateway.presentation.auth.v1.response.SocialLoginResponse
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.oauth.client.toCommand
import tamago.server.gateway.security.oauth.service.OAuthService

@RestController
class AuthController(
    private val oauthService: OAuthService,
    private val userLoginUseCase: UserLoginUseCase
) : AuthApi {

    @PostMapping("/api/v1/auth/social-login/kakao")
    override fun socialKakaoLogin(@RequestBody request: TokenRequest): CustomResponse<SocialLoginResponse> {
        val kakaoUserInfo = oauthService.getKakaoUserInfo(request.token)
        val dto = userLoginUseCase.login(kakaoUserInfo.toCommand())

        return CustomResponse.ok(
            SocialLoginResponse(
                accessToken = dto.accessToken,
                refreshToken = dto.refreshToken,
                isNewUser = dto.isNewUser
            )
        )
    }

    @PostMapping("/v1/auth/social-login/apple")
    override fun socialAppleLogin(request: TokenRequest): CustomResponse<Void> {
        return CustomResponse.ok()
    }
}