package tamago.server.core.notification.domain.vo

@JvmInline
value class NotificationId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
