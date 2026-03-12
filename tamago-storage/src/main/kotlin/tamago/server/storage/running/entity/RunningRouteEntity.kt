package tamago.server.storage.running.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Point
import tamago.server.storage.support.BaseTimeEntity

@Entity
@Table(name = "t_running_routes")
class RunningRouteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "running_route_id")
    val id: Long? = null,
    @Column(name = "running_id", nullable = false)
    val runningId: Long,
    @Column(name = "route", columnDefinition = "LINESTRING")
    val route: LineString? = null,
    @Column(name = "start_point", columnDefinition = "POINT")
    val startPoint: Point? = null,
    @Column(name = "end_point", columnDefinition = "POINT")
    val endPoint: Point? = null,
) : BaseTimeEntity()
