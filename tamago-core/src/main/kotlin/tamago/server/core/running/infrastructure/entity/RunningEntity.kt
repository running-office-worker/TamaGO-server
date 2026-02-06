package tamago.server.core.running.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "t_running")
class RunningEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "running_id")
    val id: Long? = null,

    val pace: Double? = null,

    val time: LocalTime? = null,

    val kcal: Int? = null,

    val kilometre: Double? = null,

    val startedAt: LocalDateTime? = null,

    val finishedAt: LocalDateTime? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,
) : BaseTimeEntity()
