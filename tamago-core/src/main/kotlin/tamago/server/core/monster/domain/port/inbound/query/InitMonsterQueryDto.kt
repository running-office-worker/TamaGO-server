package tamago.server.core.monster.domain.port.inbound.query

data class InitMonsterQueryDto(
    val ownedMonsterId: Long,
    val monsterId: Long,
    val nickname: String?,
    val status: String,
)
