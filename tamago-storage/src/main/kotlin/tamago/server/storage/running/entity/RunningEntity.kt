package tamago.server.storage.running.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.storage.support.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "t_running")
class RunningEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "running_id")
    val id: Long? = null,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @Column(name = "owned_monster_id", nullable = false)
    val ownedMonsterId: Long,
    val pace: Double? = null,
    val cadence: Int? = null,
    val calories: Int? = null,
    val distance: Double? = null,
    @Column(name = "elevation_gain")
    val elevationGain: Double? = null,
    val heartbeat: Int? = null,
    @Column(name = "elapsed_time")
    val elapsedTime: Int? = null,
    val startedAt: LocalDateTime? = null,
    val finishedAt: LocalDateTime? = null,
) : BaseTimeEntity()
