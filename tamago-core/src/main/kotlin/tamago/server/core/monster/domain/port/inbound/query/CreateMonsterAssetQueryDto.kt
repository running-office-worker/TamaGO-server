package tamago.server.core.monster.domain.port.inbound.query

data class CreateMonsterAssetQueryDto(
    val uploadUrl: String,
    val previewUrl: String,
    val assetKey: String,
)
