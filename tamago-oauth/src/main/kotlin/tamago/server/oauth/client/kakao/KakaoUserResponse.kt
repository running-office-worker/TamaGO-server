package tamago.server.oauth.client.kakao

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

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
