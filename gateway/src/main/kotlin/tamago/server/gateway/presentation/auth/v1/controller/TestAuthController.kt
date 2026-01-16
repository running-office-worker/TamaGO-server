package tamago.server.gateway.presentation.auth.v1.controller

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tamago.server.gateway.presentation.auth.v1.api.TestAuthApi
import tamago.server.gateway.presentation.auth.v1.request.LoginRequest
import tamago.server.gateway.presentation.auth.v1.request.SignupRequest
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.presentation.auth.v1.response.SignupResponse
import tamago.server.gateway.response.CustomResponse

@Profile("local", "dev")
@RestController
@RequestMapping("/api/v1/auth")
class TestAuthController : TestAuthApi {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    override fun signup(@RequestBody request: SignupRequest): CustomResponse<SignupResponse> {
        // TODO: 회원가입 로직 구현
        throw NotImplementedError("회원가입 기능이 아직 구현되지 않았습니다.")
    }

    @PostMapping("/login")
    override fun login(@RequestBody request: LoginRequest): CustomResponse<LoginResponse> {
        // TODO: 로그인 로직 구현
        throw NotImplementedError("로그인 기능이 아직 구현되지 않았습니다.")
    }
}
