package tamago.server.core.monster.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.RunningCompletedEvent
import tamago.server.core.common.event.UserDeletedEvent
import tamago.server.core.common.event.UserRestoredEvent
import tamago.server.core.common.event.UserSignedUpEvent
import tamago.server.core.monster.application.service.MonsterCommandService
import tamago.server.core.monster.application.service.MonsterQueryService

private val logger = KotlinLogging.logger {}

@Component
class MonsterEventListener(
    private val monsterQueryService: MonsterQueryService,
    private val monsterCommandService: MonsterCommandService,
) {
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserSignedUp(event: UserSignedUpEvent) {
        try {
            monsterQueryService
                .getDefaultMonster()
                ?.let { monster -> monsterCommandService.ownMonster(monster.id!!, event.userId) }
        } catch (e: Exception) {
            logger.error(e) { "회원 가입 후 기본 몬스터 지급 오류 - userId: ${event.userId}" }
        }
    }

    @EventListener
    fun onUserDeleted(event: UserDeletedEvent) {
        monsterCommandService.hardDeleteOwnedMonstersByUserId(event.userId)
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserRestored(event: UserRestoredEvent) {
        try {
            val ownedMonsters = monsterQueryService.getDeletedOwnedMonstersByUserId(event.userId)
            ownedMonsters.forEach { monsterCommandService.restore(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 복구 후 보유 몬스터 복구 오류 - userId: ${event.userId}" }
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onRunningCompleted(event: RunningCompletedEvent) {
        applyEarnedXp(event)
        evolutionMonsters(event)
        unlockMonsters(event)
    }

    private fun applyEarnedXp(event: RunningCompletedEvent) {
        try {
            val xpResult = monsterQueryService.calculateEarnedXp(event.ownedMonsterId, event.distance)
            monsterCommandService.addEarnedXp(event.ownedMonsterId, xpResult.earnedXp)
        } catch (e: Exception) {
            logger.error(e) { "러닝 완료 후 XP 적용 오류 - userId: ${event.userId}, ownedMonsterId: ${event.ownedMonsterId}" }
        }
    }

    private fun evolutionMonsters(event: RunningCompletedEvent) {
        try {
            var ownedMonster = monsterQueryService.getOwnedMonster(event.ownedMonsterId)

            while (true) {
                val monster = monsterQueryService.get(ownedMonster.monsterId)
                val nextMonsterId = monster.nextMonsterId ?: break
                val evolutionXp = monster.evolutionXp ?: break

                if ((ownedMonster.havingXp ?: 0) < evolutionXp) break

                monsterCommandService.evolve(ownedMonster, nextMonsterId)
            }
        } catch (e: Exception) {
            logger.error(
                e,
            ) { "러닝 완료 후 몬스터 진화 처리 오류 - userId: ${event.userId}, ownedMonsterId: ${event.ownedMonsterId}" }
        }
    }

    private fun unlockMonsters(event: RunningCompletedEvent) {
        try {
            val allFirstStageMonsters = monsterQueryService.getAllFirstStageWithUnlockPolicies()

            // 이미 보유한 몬스터 ID 목록 조회
            val ownedMonsters = monsterQueryService.getOwnedMonstersByUserId(event.userId)
            val ownedMonsterIds = ownedMonsters.map { it.monsterId }.toSet()

            allFirstStageMonsters
                .filter { monster -> monster.id !in ownedMonsterIds } // 이미 보유한 몬스터는 제외
                .filter { monster ->
                    monster.unlockPolicies.any {
                        // 여러 해금 조건 중 하나라도 만족하면 몬스터 해금
                        it.isSatisfiedBy(
                            event.totalDistance,
                            event.totalDurationMinutes,
                        )
                    }
                }.forEach { monster ->
                    monsterCommandService.ownMonster( // 해금 조건 만족하는 몬스터 소유 처리
                        monster.id!!,
                        event.userId,
                    )
                }
        } catch (e: Exception) {
            logger.error(e) { "러닝 완료 후 몬스터 해금 처리 오류 - userId: ${event.userId}" }
        }
    }
}
