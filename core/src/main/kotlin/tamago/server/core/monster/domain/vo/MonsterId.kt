package tamago.server.core.monster.domain.vo

@JvmInline
value class MonsterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
