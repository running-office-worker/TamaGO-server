package tamago.server.gateway.security.oauth.service

import feign.FeignException
import org.springframework.stereotype.Service
import tamago.server.gateway.security.oauth.client.AppleClient
import tamago.server.gateway.security.oauth.client.AppleClientResult
import tamago.server.gateway.security.oauth.client.KakaoClient
import tamago.server.gateway.security.oauth.client.KakaoClientResult
import tamago.server.gateway.security.oauth.exception.AuthenticationErrorException

@Service
class OAuthService(
    private val kakaoClient: KakaoClient,
    private val appleClient: AppleClient,
) {
    fun getKakaoUserInfo(token: String): KakaoClientResult =
        try {
            kakaoClient.getUserInfo(token)
        } catch (e: FeignException) {
            if (e.status() == 401) {
                throw AuthenticationErrorException()
            } else {
                throw AuthenticationErrorException()
            }
        }

    fun getAppleUserInfo(token: String): AppleClientResult {
        if (!appleClient.verify(token)) throw AuthenticationErrorException()
        return appleClient.getUserInfo(token)
    }
}