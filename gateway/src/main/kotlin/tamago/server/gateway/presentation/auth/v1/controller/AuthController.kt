package tamago.server.gateway.presentation.auth.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserFacade
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.gateway.presentation.auth.v1.api.AuthApi
import tamago.server.gateway.presentation.auth.v1.request.AppleLoginRequest
import tamago.server.gateway.presentation.auth.v1.request.KakaoLoginRequest
import tamago.server.gateway.presentation.auth.v1.request.ReissueRequest
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.presentation.auth.v1.response.SocialLoginResponse
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.oauth.client.toCommand
import tamago.server.gateway.security.oauth.service.OAuthService

@RestController
class AuthController(
    private val oauthService: OAuthService,
    private val userFacade: UserFacade,
) : AuthApi {

    @PostMapping("/api/v1/auth/social-login/kakao")
    override fun socialKakaoLogin(@RequestBody request: KakaoLoginRequest): CustomResponse<SocialLoginResponse> {
        val kakaoUser = oauthService.getKakaoUserInfo(request.token)
        val dto = userFacade.socialLogin(kakaoUser.toCommand(OAuthProvider.KAKAO))

        return CustomResponse.ok(
            SocialLoginResponse(
                accessToken = dto.accessToken,
                refreshToken = dto.refreshToken,
                isNewUser = dto.isNewUser
            )
        )
    }

    @PostMapping("/api/v1/auth/social-login/apple")
    override fun socialAppleLogin(@RequestBody request: AppleLoginRequest): CustomResponse<SocialLoginResponse> {
        val appleUser = oauthService.getAppleUserInfo(request.token)
        val dto = userFacade.socialLogin(appleUser.toCommand(OAuthProvider.APPLE))

        return CustomResponse.ok(
            SocialLoginResponse(
                accessToken = dto.accessToken,
                refreshToken = dto.refreshToken,
                isNewUser = dto.isNewUser
            )
        )
    }

    @PostMapping("/api/v1/auth/reissue")
    override fun reissue(@RequestBody request: ReissueRequest): CustomResponse<LoginResponse> {
        val token = userFacade.reissueToken(request.refreshToken)

        return CustomResponse.ok(
            LoginResponse(
                userId = token.userId,
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
            )
        )
    }
}