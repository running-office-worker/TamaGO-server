package tamago.server.core.monster.infrastructure.mapper

import tamago.server.core.monster.domain.aggregate.MonsterEvolutionPolicy
import tamago.server.core.monster.domain.vo.MonsterId
import tamago.server.core.monster.domain.vo.MonsterEvolutionPolicyId
import tamago.server.core.monster.infrastructure.entity.MonsterEntity
import tamago.server.core.monster.infrastructure.entity.MonsterEvolutionPolicyEntity

object MonsterEvolutionPolicyMapper {
    fun toEntity(domain: MonsterEvolutionPolicy, monster: MonsterEntity): MonsterEvolutionPolicyEntity {
        return MonsterEvolutionPolicyEntity(
            id = domain.id?.value,
            monster = monster,
            ruleType = domain.ruleType,
            ruleValue = domain.ruleValue,
        )
    }

    fun toDomain(entity: MonsterEvolutionPolicyEntity?): MonsterEvolutionPolicy? {
        if (entity == null) return null

        return MonsterEvolutionPolicy(
            id = entity.id?.let { MonsterEvolutionPolicyId(it) },
            monsterId = MonsterId(entity.monster.id!!),
            ruleType = entity.ruleType,
            ruleValue = entity.ruleValue,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
