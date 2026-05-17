package tamago.server.core.monster.domain.enum

enum class AssetType(
    val contentType: String,
    val extension: String,
) {
    /** 몬스터 로띠 애니메이션 */
    M_LOTTIE("application/zip+dotlottie", "lottie"),

    /** 몬스터 기본 이미지 */
    M_SVG("image/svg+xml", "svg"),

    /** 썸네일 SVG 이미지 (미사용) */
    S_SVG("image/svg+xml", "svg"),

    /** 썸네일 PNG 이미지 */
    S_PNG("image/png", "png"),

    /** 몬스터 기본 이미지 (PNG) */
    M_PNG("image/png", "png"),

    /** 몬스터 그룹 왼쪽 배경 PNG 이미지 */
    LBG_PNG("image/png", "png"),

    /** 몬스터 그룹 오른쪽 배경 PNG 이미지 */
    RBG_PNG("image/png", "png"),
}
