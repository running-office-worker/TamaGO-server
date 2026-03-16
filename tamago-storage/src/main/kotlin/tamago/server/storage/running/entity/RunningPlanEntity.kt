package tamago.server.storage.running.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.storage.support.BaseTimeEntity

@Entity
@Table(name = "t_running_plans")
class RunningPlanEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "running_plan_id")
    val id: Long? = null,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "goal_distance", nullable = false)
    val goalDistance: Double,
) : BaseTimeEntity()
