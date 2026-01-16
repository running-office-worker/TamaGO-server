package tamago.server.gateway.presentation.auth.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.gateway.presentation.auth.v1.request.LoginRequest
import tamago.server.gateway.presentation.auth.v1.request.SignupRequest
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.presentation.auth.v1.response.SignupResponse
import tamago.server.gateway.response.CustomResponse

@Tag(name = "Test Auth API", description = "테스트용 인증 API (local, dev 환경에서만 사용 가능)")
interface TestAuthApi {
    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 회원가입을 처리합니다.")
    fun signup(request: SignupRequest): CustomResponse<SignupResponse>

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인을 처리합니다.")
    fun login(request: LoginRequest): CustomResponse<LoginResponse>
}
