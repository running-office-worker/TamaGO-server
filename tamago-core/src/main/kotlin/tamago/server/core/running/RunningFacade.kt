package tamago.server.core.running

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.running.domain.event.RunningCompletedEvent
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterCommandUseCase
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import tamago.server.core.running.domain.port.inbound.query.MonthlyRunningQueryDto
import tamago.server.core.running.domain.port.inbound.query.RunningFinishQueryDto
import tamago.server.core.running.application.service.RunningCommandService
import tamago.server.core.running.application.service.RunningQueryService

@Component
class RunningFacade(
    private val runningCommandService: RunningCommandService,
    private val runningQueryService: RunningQueryService,
    private val monsterCommandUseCase: MonsterCommandUseCase,
    private val monsterQueryUseCase: MonsterQueryUseCase,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun saveRunningData(command: SaveRunningCommandDto): RunningFinishQueryDto {
        val running = runningCommandService.save(command)

        publishRunningCompletedEvent(command)

        // 같이 뛴 몬스터의 진화 체인 조회
        val ownedMonster = monsterQueryUseCase.getOwnedMonster(command.ownedMonsterId)
        val evolutionChain = monsterQueryUseCase.getEvolutionChain(ownedMonster.monsterId)

        // 획득한 경험치 계산 및 저장
        val earnedXp = evolutionChain.first { it.id == ownedMonster.monsterId }
            .evolutionPolicy?.calculateXp(command.distance) ?: 0
        monsterCommandUseCase.addEarnedXp(ownedMonster, earnedXp)

        return RunningFinishQueryDto.of(
            running = running,
            ownedMonster = ownedMonster,
            evolutionChain = evolutionChain,
            earnedXp = earnedXp,
            startedAt = command.startedAt,
            finishedAt = command.finishedAt,
        )
    }

    private fun publishRunningCompletedEvent(command: SaveRunningCommandDto) {
        applicationEventPublisher.publishEvent(
            RunningCompletedEvent(
                userId = command.userId,
                startedAt = command.startedAt,
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getMonthlyRunningData(userId: UserId, year: Int, month: Int): MonthlyRunningQueryDto {
        val runnings = runningQueryService.getMonthlyRunnings(userId, year, month)

        // ownedMonsterId → monsterId 매핑 (distinct)
        val ownedMonsterIds = runnings.map { it.ownedMonsterId }.distinct()
        val ownedMonsterMap = ownedMonsterIds.associateWith { monsterQueryUseCase.getOwnedMonster(it) }

        // monsterId → imageUrl 매핑 (distinct)
        val monsterIds = ownedMonsterMap.values.map { it.monsterId }.distinct()
        val monsterImageMap = monsterIds.associateWith { monsterQueryUseCase.getMonsterPngUrl(it) }

        // 날짜별 그룹핑
        val dailyRunnings = runnings
            .groupBy { it.startedAt!!.toLocalDate() }
            .entries
            .sortedBy { it.key }
            .map { (date, runs) ->
                MonthlyRunningQueryDto.DailyRunningDto(
                    date = date,
                    runs = runs.map { running ->
                        val ownedMonster = ownedMonsterMap[running.ownedMonsterId]
                        val imageUrl = ownedMonster?.let { monsterImageMap[it.monsterId] }
                        MonthlyRunningQueryDto.RunDetailDto.from(running, imageUrl)
                    },
                )
            }

        return MonthlyRunningQueryDto(
            dailyRunnings = dailyRunnings,
            summary = MonthlyRunningQueryDto.MonthlySummaryDto.from(runnings),
        )
    }

}
