package tamago.server.core.running.infrastructure.mapper

import tamago.server.core.common.vo.OwnedMonsterId
import tamago.server.core.common.vo.UserId
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.infrastructure.entity.RunningEntity

object RunningMapper {
    fun toEntity(running: Running): RunningEntity =
        RunningEntity(
            id = running.id?.value,
            userId = running.userId.value,
            ownedMonsterId = running.ownedMonsterId.value,
            pace = running.pace,
            cadence = running.cadence,
            calories = running.calories,
            distance = running.distance,
            elevationGain = running.elevationGain,
            heartbeat = running.heartbeat,
            elapsedTime = running.elapsedTime,
            startedAt = running.startedAt,
            finishedAt = running.finishedAt,
        )

    fun toDomain(entity: RunningEntity?): Running? {
        if (entity == null) return null

        return Running(
            id = entity.id?.let { RunningId(it) },
            userId = UserId(entity.userId),
            ownedMonsterId = OwnedMonsterId(entity.ownedMonsterId),
            pace = entity.pace,
            cadence = entity.cadence,
            calories = entity.calories,
            distance = entity.distance,
            elevationGain = entity.elevationGain,
            heartbeat = entity.heartbeat,
            elapsedTime = entity.elapsedTime,
            startedAt = entity.startedAt,
            finishedAt = entity.finishedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
