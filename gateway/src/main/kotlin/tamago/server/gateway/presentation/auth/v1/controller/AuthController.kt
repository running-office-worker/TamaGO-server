package tamago.server.gateway.presentation.auth.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.gateway.presentation.auth.v1.api.AuthApi
import tamago.server.gateway.presentation.auth.v1.request.TokenRequest
import tamago.server.gateway.response.CustomResponse
import tamago.server.gateway.security.oauth.service.OAuthService

@RestController
class AuthController(
    private val oauthService: OAuthService,
) : AuthApi {

    @PostMapping("/v1/auth/social-login/kakao")
    override fun socialKakaoLogin(@RequestBody request: TokenRequest): CustomResponse<Void> {
        oauthService.getKaKaoUserInfo(request.token)
        return CustomResponse.ok()
    }

    @PostMapping("/v1/auth/social-login/apple")
    override fun socialAppleLogin(request: TokenRequest): CustomResponse<Void> {
        return CustomResponse.ok()
    }
}