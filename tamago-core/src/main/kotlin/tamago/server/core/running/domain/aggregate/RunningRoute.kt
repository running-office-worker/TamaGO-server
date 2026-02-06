package tamago.server.core.running.domain.aggregate

import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import tamago.server.core.running.domain.vo.RunningId
import tamago.server.core.running.domain.vo.RunningRouteId
import java.time.LocalDateTime

class RunningRoute(
    val id: RunningRouteId? = null,
    val runningId: RunningId,
    val route: LineString? = null,
    val startPoint: Point? = null,
    val endPoint: Point? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            runningId: RunningId,
            route: LineString? = null,
            startPoint: Point? = null,
            endPoint: Point? = null,
        ): RunningRoute {
            return RunningRoute(
                runningId = runningId,
                route = route,
                startPoint = startPoint,
                endPoint = endPoint,
            )
        }
    }
}
