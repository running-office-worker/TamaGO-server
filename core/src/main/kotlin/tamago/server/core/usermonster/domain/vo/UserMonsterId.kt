package tamago.server.core.usermonster.domain.vo

@JvmInline
value class UserMonsterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
