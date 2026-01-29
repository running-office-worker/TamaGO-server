package tamago.server.core.letter.domain.vo

@JvmInline
value class ReceivedLetterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
