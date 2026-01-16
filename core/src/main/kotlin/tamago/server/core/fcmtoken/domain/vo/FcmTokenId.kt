package tamago.server.core.fcmtoken.domain.vo

@JvmInline
value class FcmTokenId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
