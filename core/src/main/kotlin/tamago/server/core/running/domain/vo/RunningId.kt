package tamago.server.core.running.domain.vo

@JvmInline
value class RunningId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
