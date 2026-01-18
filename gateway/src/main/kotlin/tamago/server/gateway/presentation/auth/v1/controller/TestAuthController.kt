package tamago.server.gateway.presentation.auth.v1.controller

import jakarta.validation.Valid
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserFacade
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.gateway.presentation.auth.v1.api.TestAuthApi
import tamago.server.gateway.presentation.auth.v1.request.LoginRequest
import tamago.server.gateway.presentation.auth.v1.request.SignupRequest
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.presentation.auth.v1.response.SignupResponse
import tamago.server.gateway.response.CustomResponse

@Profile("local", "dev")
@RestController
@RequestMapping("/api/v1/auth")
class TestAuthController(
    private val userFacade: UserFacade,
) : TestAuthApi {

    @PostMapping("/signup")
    override fun signup(@RequestBody @Valid request: SignupRequest): CustomResponse<SignupResponse> {
        userFacade.signUp(
            SignUpCommandDto(
                email = request.email,
                password = request.password,
            )
        )

        return CustomResponse.ok()
    }

    @PostMapping("/login")
    override fun login(@RequestBody request: LoginRequest): CustomResponse<LoginResponse> {
        // TODO: 로그인 로직 구현
        throw NotImplementedError("로그인 기능이 아직 구현되지 않았습니다.")
    }
}
