package tamago.server.core.monster.domain.port.inbound.query

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster

data class MonsterDexQueryDto(
    val monsterId: Long,
    val nickname: String?,
    val unlockDescription: String?,
    val owned: Boolean,
    val ownedMonster: OwnedMonsterInfo?,
) {
    companion object {
        fun of(
            monster: Monster,
            ownedMonster: OwnedMonster?,
        ): MonsterDexQueryDto =
            MonsterDexQueryDto(
                monsterId = monster.id!!.value,
                nickname = monster.nickname,
                unlockDescription = monster.unlockPolicies.firstOrNull()?.description,
                owned = ownedMonster != null,
                ownedMonster = ownedMonster?.let { OwnedMonsterInfo.of(it) },
            )
    }

    data class OwnedMonsterInfo(
        val ownedMonsterId: Long,
        val havingXp: Int?,
        val status: String?,
    ) {
        companion object {
            fun of(ownedMonster: OwnedMonster): OwnedMonsterInfo =
                OwnedMonsterInfo(
                    ownedMonsterId = ownedMonster.id!!.value,
                    havingXp = ownedMonster.havingXp,
                    status = ownedMonster.status?.name,
                )
        }
    }
}
