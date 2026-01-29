package tamago.server.gateway.presentation.auth.v1.controller

import jakarta.validation.Valid
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.user.UserFacade
import tamago.server.core.user.domain.port.inbound.command.SignUpCommandDto
import tamago.server.core.user.domain.port.inbound.command.TestLoginCommandDto
import tamago.server.gateway.presentation.auth.v1.api.TestAuthApi
import tamago.server.gateway.presentation.auth.v1.request.LoginRequest
import tamago.server.gateway.presentation.auth.v1.request.SignupRequest
import tamago.server.gateway.presentation.auth.v1.response.LoginResponse
import tamago.server.gateway.common.response.CustomResponse

@Profile("local", "dev")
@RestController
@RequestMapping("/api/v1/auth")
class TestAuthController(
    private val userFacade: UserFacade,
) : TestAuthApi {

    @PostMapping("/signup")
    override fun signup(@RequestBody @Valid request: SignupRequest): CustomResponse<Void> {
        userFacade.signUp(
            SignUpCommandDto(
                email = request.email,
                password = request.password,
            )
        )

        return CustomResponse.ok()
    }

    @PostMapping("/login")
    override fun login(@RequestBody @Valid request: LoginRequest): CustomResponse<LoginResponse> {
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
