package tamago.server.core.user.domain.aggregate

data class UserRunningData(
    val totalKilo: Double? = null,
) {
    fun addKilo(kilo: Double): UserRunningData = copy(totalKilo = (totalKilo ?: 0.0) + kilo)
}
