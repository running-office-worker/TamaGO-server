package tamago.server.core.monster.domain.vo

@JvmInline
value class OwnedMonsterId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
