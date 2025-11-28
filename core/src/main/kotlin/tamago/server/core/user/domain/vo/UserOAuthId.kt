package tamago.server.core.user.domain.vo

@JvmInline
value class UserOAuthId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
