package tamago.server.core.letter.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity
import tamago.server.core.letter.domain.enum.ReadStatus

@Entity
@Table(name = "t_received_letters")
class ReceivedLetterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "received_letter_id")
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "letter_id",
        nullable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val letter: LetterEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "read_status")
    var readStatus: ReadStatus = ReadStatus.UNREAD,
) : BaseTimeEntity()
