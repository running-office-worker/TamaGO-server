package tamago.server.core.common.vo

@JvmInline
value class UserId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
