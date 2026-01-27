package tamago.server.core.user.domain.aggregate

data class UserRunningData(
    val goalKilo: Int? = null,
    val totalKilo: Int? = null,
) {
    fun updateGoalKilo(goalKilo: Int): UserRunningData {
        return copy(goalKilo = goalKilo)
    }

    fun addKilo(kilo: Int): UserRunningData {
        return copy(totalKilo = (totalKilo ?: 0) + kilo)
    }
}
