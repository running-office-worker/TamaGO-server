package tamago.server.core.running.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
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

    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    val userId: Long,
) : BaseTimeEntity()
