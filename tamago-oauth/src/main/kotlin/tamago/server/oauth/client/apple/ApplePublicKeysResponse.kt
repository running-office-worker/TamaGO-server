package tamago.server.oauth.client.apple

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
