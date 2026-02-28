package tamago.server.core.running.infrastructure.repository

data class RunningStatsByMonsterRow(
    val ownedMonsterId: Long,
    val totalDistance: Double,
    val runCount: Long,
    val totalElapsedSeconds: Long,
)
