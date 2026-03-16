package tamago.server.core.running.domain.vo

@JvmInline
value class RunningPlanId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
