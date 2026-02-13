package tamago.server.core.running

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tamago.server.core.common.vo.UserId
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import tamago.server.core.running.domain.port.inbound.query.MonthlyRunningQueryDto
import tamago.server.core.running.domain.port.inbound.query.RunningFinishQueryDto
import java.time.Duration

@Component
class RunningFacade(
    private val runningCommandUseCase: RunningCommandUseCase,
    private val runningQueryUseCase: RunningQueryUseCase,
    private val monsterQueryUseCase: MonsterQueryUseCase,
) {
    @Transactional
    fun saveRunningData(command: SaveRunningCommandDto): RunningFinishQueryDto {
        val running = runningCommandUseCase.save(command)

        val ownedMonster = monsterQueryUseCase.getOwnedMonster(command.ownedMonsterId)
        val evolutionChain = monsterQueryUseCase.getEvolutionChain(ownedMonster.monsterId)
        val currentMonster = evolutionChain.first { it.id == ownedMonster.monsterId }

        val elapsedSeconds = Duration.between(command.startedAt, command.finishedAt).seconds.toInt()

        return RunningFinishQueryDto(
            pace = running.pace?.toInt() ?: 0,
            cadence = running.cadence ?: 0,
            elapsedTime = elapsedSeconds,
            totalCalories = running.calories ?: 0,
            originXp = ownedMonster.havingXp ?: 0,
            earnedXp = currentMonster.evolutionPolicy?.calculateXp(command.distance) ?: 0, // TODO: earnedXp 저장
            evolutionStages = evolutionChain.mapIndexed { index, monster ->
                RunningFinishQueryDto.EvolutionStageDto(
                    stage = index + 1,
                    monsterId = monster.id!!.value,
                    evolutionXp = monster.evolutionXp,
                    current = monster.id == ownedMonster.monsterId,
                )
            },
        )
    }

    @Transactional(readOnly = true)
    fun getMonthlyRunningData(userId: UserId, year: Int, month: Int): MonthlyRunningQueryDto {
        val runnings = runningQueryUseCase.getMonthlyRunnings(userId, year, month)

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
