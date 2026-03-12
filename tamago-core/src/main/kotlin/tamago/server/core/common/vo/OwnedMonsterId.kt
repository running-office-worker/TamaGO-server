package tamago.server.core.common.vo

@JvmInline
value class OwnedMonsterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
