package tamago.server.core.monster.domain.vo

sealed interface AssetMetadata

data class BackgroundAssetMetadata(
    val backgroundColor: String?,
    val startHour: Int? = null,
    val endHour: Int? = null,
) : AssetMetadata {
    fun containsHour(hour: Int): Boolean {
        require(hour in 0..23) { "hour must be between 0 and 23" }

        val start = startHour ?: return false
        val end = endHour ?: return false

        return when {
            start < end -> hour in start until end
            start > end -> hour >= start || hour < end
            else -> true
        }
    }
}
