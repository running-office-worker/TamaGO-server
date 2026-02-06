package tamago.server.core.monster.domain.vo

@JvmInline
value class MonsterEvolutionPolicyId(
    val value: Long,
) {
    override fun toString(): String = value.toString()
}
