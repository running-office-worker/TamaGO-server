package tamago.server.core.user.application.service

import org.springframework.stereotype.Service
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.user.UserLoginUseCase
import tamago.server.core.user.application.exception.UserSaveErrorException
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.OAuthProvider
import tamago.server.core.user.domain.port.inbound.command.LoginCommandDto
import tamago.server.core.user.domain.port.inbound.query.TokenQueryDto
import tamago.server.core.user.domain.port.outbound.UserPersistencePort
import tamago.server.core.user.domain.vo.UserId

@Service
class UserLoginService(
    private val userPersistencePort: UserPersistencePort,
    private val jwtTokenProvider: JwtTokenProvider
) : UserLoginUseCase {

    override fun login(command: LoginCommandDto): TokenQueryDto {
        val (userId, isNewUser) = userPersistencePort.findByExternalId(OAuthProvider.KAKAO, command.externalId)?.id
            ?.let { existingUser -> existingUser to false }
            ?: run { signUp(command) to true }

        return TokenQueryDto(
            accessToken = jwtTokenProvider.generateAccessToken(userId),
            refreshToken = jwtTokenProvider.generateRefreshToken(userId),
            isNewUser = isNewUser
        )
    }

    private fun signUp(command: LoginCommandDto): UserId =
        userPersistencePort.save(User.create(command.name)).id
            ?: throw UserSaveErrorException()
}
