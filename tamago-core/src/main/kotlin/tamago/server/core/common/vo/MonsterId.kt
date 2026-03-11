package tamago.server.core.common.vo

@JvmInline
value class MonsterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
