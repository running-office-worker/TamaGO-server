package tamago.server.core.letter.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import tamago.server.core.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_letters")
class LetterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_id")
    val id: Long? = null,
    val title: String,
    @Lob
    @Column(columnDefinition = "TEXT")
    val content: String,
) : BaseTimeEntity()
