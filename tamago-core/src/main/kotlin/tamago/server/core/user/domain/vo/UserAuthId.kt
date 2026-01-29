package tamago.server.core.user.domain.vo

@JvmInline
value class UserAuthId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
