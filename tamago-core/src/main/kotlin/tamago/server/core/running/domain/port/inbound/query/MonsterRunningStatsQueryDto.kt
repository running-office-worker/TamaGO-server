package tamago.server.core.running.domain.port.inbound.query

data class MonsterRunningStatsQueryDto(
    val ownedMonsterId: Long,
    val totalDistance: Double,
    val runCount: Int,
    val totalTimeMinutes: Long,
)
