package tamago.server.core.monster.domain.vo

sealed interface AssetMetadata

data class BackgroundAssetMetadata(
    val backgroundColor: String?,
) : AssetMetadata
