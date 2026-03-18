package tamago.server.core.monster.application.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import tamago.server.core.common.event.RunningCompletedEvent
import tamago.server.core.common.event.UserDeletedEvent
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
            // TODO: Sentry 연동
            logger.error(e) { "회원 가입 후 기본 몬스터 지급 오류 - userId: ${event.userId}" }
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onUserDeleted(event: UserDeletedEvent) {
        try {
            val ownedMonsters = monsterQueryService.getOwnedMonstersByUserId(event.userId)
            ownedMonsters.forEach { monsterCommandService.delete(it) }
        } catch (e: Exception) {
            logger.error(e) { "회원 탈퇴 후 보유 몬스터 삭제 오류 - userId: ${event.userId}" }
        }
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun onRunningCompleted(event: RunningCompletedEvent) {
        try {
            val allFirstStageMonsters = monsterQueryService.getAllFirstStageWithUnlockPolicies()

            // 이미 보유한 몬스터 ID 목록 조회
            val ownedMonsters = monsterQueryService.getOwnedMonstersByUserId(event.userId)
            val ownedMonsterIds = ownedMonsters.map { it.monsterId }.toSet()

            allFirstStageMonsters
                .filter { monster -> monster.id !in ownedMonsterIds } // 이미 보유한 몬스터는 제외
                .filter { monster ->
                    monster.unlockPolicies.any {
                        it.isSatisfiedBy(
                            event.totalDistance,
                            event.totalDurationMinutes,
                        )
                    }
                }.forEach { monster -> monsterCommandService.ownMonster(monster.id!!, event.userId) }
        } catch (e: Exception) {
            // TODO: Sentry 연동
            logger.error(e) { "러닝 완료 후 몬스터 해금 처리 오류 - userId: ${event.userId}" }
        }
    }
}
