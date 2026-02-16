package tamago.server.core.running.infrastructure.repository

import org.springframework.stereotype.Repository
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.core.running.infrastructure.mapper.RunningMapper
import java.time.LocalDate

@Repository
class RunningPersistenceAdapter(
    private val runningJpaRepository: RunningJpaRepository,
) : RunningPersistencePort {

    override fun save(running: Running): Running =
        RunningMapper.toDomain(runningJpaRepository.save(RunningMapper.toEntity(running)))!!

    override fun findAllByUserIdAndMonth(userId: UserId, year: Int, month: Int): List<Running> {
        val monthStart = LocalDate.of(year, month, 1).atStartOfDay()
        val monthEnd = monthStart.plusMonths(1)
        return runningJpaRepository
            .findAllByUserIdAndStartedAtGreaterThanEqualAndStartedAtLessThanAndDeletedAtIsNull(
                userId = userId.value,
                monthStart = monthStart,
                monthEnd = monthEnd,
            )
            .mapNotNull { RunningMapper.toDomain(it) }
    }
}
