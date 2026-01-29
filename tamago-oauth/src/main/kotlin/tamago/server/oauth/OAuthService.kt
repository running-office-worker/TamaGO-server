package tamago.server.oauth

import feign.FeignException
import org.springframework.stereotype.Service
import tamago.server.oauth.client.apple.AppleClient
import tamago.server.oauth.client.apple.AppleClientResult
import tamago.server.oauth.client.kakao.KakaoClient
import tamago.server.oauth.client.kakao.KakaoClientResult
import tamago.server.oauth.exception.AuthenticationErrorException

@Service
class OAuthService(
    private val kakaoClient: KakaoClient,
    private val appleClient: AppleClient,
) {
    fun getKakaoUserInfo(token: String): KakaoClientResult =
        try {
            kakaoClient.getUserInfo(token)
        } catch (e: FeignException) {
            throw AuthenticationErrorException()
        }

    fun getAppleUserInfo(token: String): AppleClientResult {
        if (!appleClient.verify(token)) throw AuthenticationErrorException()
        return appleClient.getUserInfo(token)
    }
}
