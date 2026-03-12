package tamago.server.storage.monster.mapper

import tamago.server.core.common.vo.MonsterId
import tamago.server.core.monster.domain.aggregate.MonsterEvolutionPolicy
import tamago.server.core.monster.domain.vo.MonsterEvolutionPolicyId
import tamago.server.storage.monster.entity.MonsterEntity
import tamago.server.storage.monster.entity.MonsterEvolutionPolicyEntity

object MonsterEvolutionPolicyMapper {
    fun toEntity(
        domain: MonsterEvolutionPolicy,
        monster: MonsterEntity,
    ): MonsterEvolutionPolicyEntity =
        MonsterEvolutionPolicyEntity(
            id = domain.id?.value,
            monster = monster,
            ruleType = domain.ruleType,
            multiplier = domain.multiplier,
        )

    fun toDomain(entity: MonsterEvolutionPolicyEntity?): MonsterEvolutionPolicy? {
        if (entity == null) return null

        return MonsterEvolutionPolicy(
            id = entity.id?.let { MonsterEvolutionPolicyId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            ruleType = entity.ruleType,
            multiplier = entity.multiplier,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
