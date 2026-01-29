package tamago.server.gateway.security.oauth.client

import org.springframework.stereotype.Component

@Component
class KakaoClient internal constructor(
    private val kakaoApi: KakaoApi,
) {
    fun getUserInfo(token: String): KakaoClientResult = kakaoApi.getKakaoUserInfo("Bearer $token").toResult()
}
