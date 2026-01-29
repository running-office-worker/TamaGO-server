package tamago.server.gateway.security.oauth.client.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import tamago.server.gateway.security.oauth.client.KakaoClientResult

@JsonIgnoreProperties(ignoreUnknown = true)
data class KaKaoUserResponse(
    val id: String,
    @field:JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount,
) {
    fun toResult(): KakaoClientResult =
        KakaoClientResult(
            externalId = id,
            email = kakaoAccount.email ?: "",
        )
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class KakaoAccount(
    val email: String?,
)
