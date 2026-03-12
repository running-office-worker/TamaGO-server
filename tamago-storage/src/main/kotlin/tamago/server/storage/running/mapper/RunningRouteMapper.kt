package tamago.server.storage.running.mapper

import tamago.server.core.running.domain.aggregate.RunningRoute
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.domain.vo.RunningRouteId
import tamago.server.storage.running.entity.RunningRouteEntity

object RunningRouteMapper {
    fun toEntity(domain: RunningRoute): RunningRouteEntity =
        RunningRouteEntity(
            id = domain.id?.value,
            runningId = domain.runningId.value,
            route = domain.route,
            startPoint = domain.startPoint,
            endPoint = domain.endPoint,
        )

    fun toDomain(entity: RunningRouteEntity?): RunningRoute? {
        if (entity == null) return null

        return RunningRoute(
            id = entity.id?.let { RunningRouteId(it) },
            runningId = RunningId(entity.runningId),
            route = entity.route,
            startPoint = entity.startPoint,
            endPoint = entity.endPoint,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            deletedAt = entity.deletedAt,
        )
    }
}
