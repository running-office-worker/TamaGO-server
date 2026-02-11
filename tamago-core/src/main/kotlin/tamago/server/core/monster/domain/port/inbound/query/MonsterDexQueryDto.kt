package tamago.server.core.monster.domain.port.inbound.query

import tamago.server.core.monster.domain.aggregate.Monster
import tamago.server.core.monster.domain.aggregate.OwnedMonster

data class MonsterDexQueryDto(
    val monsterId: Long,
    val unlockDescription: String?,
    val owned: Boolean,
    val ownedMonster: OwnedMonsterInfo?,
) {
    companion object {
        fun of(
            monster: Monster,
            ownedMonster: OwnedMonster?,
            imageUrl: String?,
        ): MonsterDexQueryDto {
            return MonsterDexQueryDto(
                monsterId = monster.id!!.value,
                unlockDescription = monster.unlockPolicies.firstOrNull()?.description,
                owned = ownedMonster != null,
                ownedMonster = ownedMonster?.let { OwnedMonsterInfo.of(it, monster, imageUrl) },
            )
        }
    }

    data class OwnedMonsterInfo(
        val ownedMonsterId: Long,
        val nickname: String?,
        val imageUrl: String?,
        val havingXp: Int?,
        val status: String?,
    ) {
        companion object {
            fun of(
                ownedMonster: OwnedMonster,
                monster: Monster,
                imageUrl: String?,
            ): OwnedMonsterInfo {
                return OwnedMonsterInfo(
                    ownedMonsterId = ownedMonster.id!!.value,
                    nickname = monster.nickname,
                    imageUrl = imageUrl,
                    havingXp = ownedMonster.havingXp,
                    status = ownedMonster.status?.name,
                )
            }
        }
    }
}
