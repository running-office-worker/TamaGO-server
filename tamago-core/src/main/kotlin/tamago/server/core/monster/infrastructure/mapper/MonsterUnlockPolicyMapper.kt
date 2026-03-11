package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.MonsterUnlockPolicy
import tamago.server.core.monster.domain.vo.MonsterUnlockPolicyId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.monster.infrastructure.entity.MonsterUnlockPolicyEntity

object MonsterUnlockPolicyMapper {
    fun toEntity(
        domain: MonsterUnlockPolicy,
        monster: MonsterEntity,
    ): MonsterUnlockPolicyEntity =
        MonsterUnlockPolicyEntity(
            id = domain.id?.value,
            monster = monster,
            ruleType = domain.ruleType,
            ruleValue = domain.ruleValue,
            description = domain.description,
        )

    fun toDomain(entity: MonsterUnlockPolicyEntity?): MonsterUnlockPolicy? {
        if (entity == null) return null

        return MonsterUnlockPolicy(
            id = entity.id?.let { MonsterUnlockPolicyId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            ruleType = entity.ruleType,
            ruleValue = entity.ruleValue,
            description = entity.description,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
