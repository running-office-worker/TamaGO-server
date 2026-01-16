package tamago.server.core.refreshtoken.domain.vo

@JvmInline
value class RefreshTokenId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
