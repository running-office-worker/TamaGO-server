package tamago.server.core.monster.domain.enum

enum class AssetType(
    val contentType: String,
    val extension: String,
) {
    PNG("image/png", "png"),
    GIF("image/gif", "gif"),

    M_LOTTIE("application/json", "json"),
    M_SVG("image/svg+xml", "svg"),
    S_PNG("image/png", "png"),
}
