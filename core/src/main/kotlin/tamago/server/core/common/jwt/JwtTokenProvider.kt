package tamago.server.core.common.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import tamago.server.core.user.domain.enum.UserRole
import tamago.server.core.user.domain.vo.UserId
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {
    fun generateAccessToken(userId: UserId, role: UserRole): String =
        makeToken(userId, role, jwtProperties.accessTokenExpiryMs, TokenType.ACCESS_TOKEN)

    fun generateRefreshToken(userId: UserId, role: UserRole): String =
        makeToken(userId, role, jwtProperties.refreshTokenExpiryMs, TokenType.REFRESH_TOKEN)

    private fun makeToken(userId: UserId, role: UserRole, expiryMillis: Long, tokenType: TokenType): String {
        val now = Date()
        val expiry = Date(now.time + expiryMillis)

        return Jwts.builder()
            .header()
            .add("typ", "JWT")
            .and()
            .issuedAt(now)
            .expiration(expiry)
            .subject(userId.value.toString())
            .claim("userId", userId.value.toString())
            .claim("role", role.name)
            .claim("tokenType", tokenType.name)
            .signWith(getSigningKey())
            .compact()
    }

    fun validateToken(token: String): Boolean =
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val userId = claims["userId", String::class.java]
        val role = claims["role", String::class.java]

        if (TokenType.valueOf(claims["tokenType", String::class.java]) != TokenType.ACCESS_TOKEN) {
            throw AuthorizationDeniedException("Invalid token type for authentication")
        }

        val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))
        val principal = User(userId, "", authorities)

        return UsernamePasswordAuthenticationToken(
            principal,
            token,
            principal.authorities
        )
    }

    private fun getClaims(token: String): Claims =
        Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).payload

    private fun getSigningKey(): SecretKey =
        Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))
}
