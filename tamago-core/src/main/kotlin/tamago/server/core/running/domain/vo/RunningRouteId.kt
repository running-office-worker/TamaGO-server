package tamago.server.core.running.domain.vo

@JvmInline
value class RunningRouteId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
