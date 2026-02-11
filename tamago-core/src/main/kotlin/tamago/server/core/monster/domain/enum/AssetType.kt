package tamago.server.core.monster.domain.enum

enum class AssetType(
    val contentType: String,
    val extension: String,
) {
    PNG("image/png", "png"),
    GIF("image/gif", "gif"),
    LOTTIE("application/json", "json"),
}
