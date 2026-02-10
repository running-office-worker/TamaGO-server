package tamago.server.core.monster

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.domain.port.inbound.query.InitMonsterQueryDto

@Component
class MonsterFacade(
    private val monsterCommandUseCase: MonsterCommandUseCase,
    private val monsterQueryUseCase: MonsterQueryUseCase,
) {
    @Transactional
    fun initRandomMonster(userId: UserId): InitMonsterQueryDto {
        val ownedMonster = monsterCommandUseCase.initRandomMonster(userId)
        val monster = monsterQueryUseCase.get(ownedMonster.monsterId)

        return InitMonsterQueryDto(
            ownedMonsterId = ownedMonster.id!!.value,
            monsterId = monster.id!!.value,
            nickname = monster.nickname,
            status = ownedMonster.status!!.name,
        )
    }
}
