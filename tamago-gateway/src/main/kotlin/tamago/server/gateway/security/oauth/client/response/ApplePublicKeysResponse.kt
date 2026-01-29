package tamago.server.gateway.security.oauth.client.response

data class ApplePublicKeysResponse(
    val keys: List<Key>,
)

data class Key(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String,
)
