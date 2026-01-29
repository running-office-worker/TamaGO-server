package tamago.server.core.running.domain.aggregate

import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.user.domain.vo.UserId
import java.time.LocalDateTime
import java.time.LocalTime

class Running(
    val id: RunningId? = null,
    val userId: UserId,
    val pace: Double? = null,
    val time: LocalTime? = null,
    val kcal: Int? = null,
    val kilometre: Double? = null,
    val startedAt: LocalDateTime? = null,
    val finishedAt: LocalDateTime? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            userId: UserId,
            pace: Double,
            time: LocalTime,
            kcal: Int,
            kilometre: Double,
            startedAt: LocalDateTime,
            finishedAt: LocalDateTime,
        ): Running {
            return Running(
                userId = userId,
                pace = pace,
                time = time,
                kcal = kcal,
                kilometre = kilometre,
                startedAt = startedAt,
                finishedAt = finishedAt,
            )
        }
    }
}
