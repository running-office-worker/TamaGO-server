package tamago.server.core.running.infrastructure.mapper

import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.infrastructure.entity.RunningEntity
import tamago.server.core.user.domain.vo.UserId

object RunningMapper {
    fun toEntity(running: Running): RunningEntity {
        return RunningEntity(
            id = running.id?.value,
            pace = running.pace,
            time = running.time,
            kcal = running.kcal,
            kilometre = running.kilometre,
            startedAt = running.startedAt,
            finishedAt = running.finishedAt,
            userId = running.userId.value,
        )
    }

    fun toDomain(entity: RunningEntity?): Running? {
        if (entity == null) return null

        return Running(
            id = entity.id?.let { RunningId(it) },
            userId = UserId(entity.userId),
            pace = entity.pace,
            time = entity.time,
            kcal = entity.kcal,
            kilometre = entity.kilometre,
            startedAt = entity.startedAt,
            finishedAt = entity.finishedAt,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
