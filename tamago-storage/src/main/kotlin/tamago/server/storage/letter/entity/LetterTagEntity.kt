package tamago.server.storage.letter.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.storage.common.entity.BaseTimeEntity

@Entity
@Table(name = "t_letter_tags")
class LetterTagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "letter_tag_id")
    val id: Long? = null,
    @Column(name = "letter_id", nullable = false)
    val letterId: Long,
    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    val tag: LetterTag,
) : BaseTimeEntity()
