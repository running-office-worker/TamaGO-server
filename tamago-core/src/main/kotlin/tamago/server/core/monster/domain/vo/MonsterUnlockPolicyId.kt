package tamago.server.core.monster.domain.vo

@JvmInline
value class MonsterUnlockPolicyId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
