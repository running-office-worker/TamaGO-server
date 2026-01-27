package tamago.server.core.refreshtoken.application.validator

import org.springframework.stereotype.Component
import tamago.server.core.common.jwt.JwtTokenProvider
import tamago.server.core.common.jwt.TokenType
import tamago.server.core.refreshtoken.application.exception.InvalidRefreshTokenException

@Component
class RefreshTokenValidator(
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun typeCheck(token: String) {
        if (jwtTokenProvider.getTokenType(token) != TokenType.REFRESH_TOKEN) {
            throw InvalidRefreshTokenException()
        }
    }

    fun validate(token: String) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw InvalidRefreshTokenException()
        }
    }
}