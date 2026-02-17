package tamago.server.gateway.presentation.auth.v1.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserFacade
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.command.TestLoginCommandDto
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.auth.v1.request.*
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.presentation.auth.v1.response.SocialLoginResponse
import tamago.server.oauth.OAuthService
import tamago.server.oauth.client.apple.toCommand
import tamago.server.oauth.client.kakao.toCommand

@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
class AuthController(
    private val oauthService: OAuthService,
    private val userFacade: UserFacade,
) {

    @Operation(summary = "카카오 소셜 로그인", description = "access token을 받아 카카오 소셜 로그인을 처리합니다.")
    @PostMapping("/api/v1/auth/social-login/kakao")
    fun socialKakaoLogin(@RequestBody request: KakaoLoginRequest): CustomResponse<SocialLoginResponse> {
        val kakaoUser = oauthService.getKakaoUserInfo(request.token)
        val dto = userFacade.socialLogin(kakaoUser.toCommand(AuthProvider.KAKAO))

        return CustomResponse.ok(
            SocialLoginResponse(
                accessToken = dto.accessToken,
                refreshToken = dto.refreshToken,
                isNewUser = dto.isNewUser
            )
        )
    }

    @Operation(summary = "애플 소셜 로그인", description = "애플 소셜 로그인을 처리합니다.")
    @PostMapping("/api/v1/auth/social-login/apple")
    fun socialAppleLogin(@RequestBody request: AppleLoginRequest): CustomResponse<SocialLoginResponse> {
        val appleUser = oauthService.getAppleUserInfo(request.token)
        val dto = userFacade.socialLogin(appleUser.toCommand(AuthProvider.APPLE))

        return CustomResponse.ok(
            SocialLoginResponse(
                accessToken = dto.accessToken,
                refreshToken = dto.refreshToken,
                isNewUser = dto.isNewUser
            )
        )
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰을 받아 액세스 토큰과 리프레시 토큰을 재발급합니다.")
    @PostMapping("/api/v1/auth/reissue")
    fun reissue(@RequestBody request: ReissueRequest): CustomResponse<LoginResponse> {
        val token = userFacade.reissueToken(request.refreshToken)

        return CustomResponse.ok(
            LoginResponse(
                userId = token.userId,
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
                newUser = token.isNewUser
            )
        )
    }

    @Operation(summary = "\uD83E\uDDEA 테스트 유저 회원가입", description = "이메일과 비밀번호로 테스트 유저를 생성 합니다.")
    @PostMapping("/api/v1/auth/signup")
    fun signup(@RequestBody @Valid request: SignupRequest): CustomResponse<Void> {
        userFacade.signUp(
            SignUpCommandDto(
                email = request.email,
                password = request.password,
            )
        )

        return CustomResponse.ok()
    }

    @Operation(summary = "\uD83E\uDDEA 테스트 유저 로그인", description = "이메일과 비밀번호로 테스트 계정에 로그인 합니다.")
    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody @Valid request: LoginRequest): CustomResponse<LoginResponse> {
        val token = userFacade.emailLogin(
            TestLoginCommandDto(
                email = request.email,
                password = request.password,
            )
        )

        return CustomResponse.ok(
            LoginResponse(
                userId = token.userId,
                accessToken = token.accessToken,
                refreshToken = token.refreshToken,
                newUser = token.isNewUser,
            )
        )
    }
}
