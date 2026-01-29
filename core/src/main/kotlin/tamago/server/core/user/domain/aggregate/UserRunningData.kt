package tamago.server.core.user.domain.aggregate

data class UserRunningData(
    val goalKilo: Int? = null,
    val totalKilo: Double? = null,
) {
    fun updateGoalKilo(goalKilo: Int): UserRunningData {
        return copy(goalKilo = goalKilo)
    }

    fun addKilo(kilo: Double): UserRunningData {
        return copy(totalKilo = (totalKilo ?: 0.0) + kilo)
    }
}
