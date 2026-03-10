package tamago.server.core.letter.domain.aggregate

import tamago.server.core.letter.domain.enum.LetterTag
import tamago.server.core.letter.domain.vo.LetterContent
import tamago.server.core.letter.domain.vo.LetterId
import java.time.LocalDateTime

class Letter(
    val id: LetterId? = null,
    val title: String,
    val content: LetterContent,
    val tags: Set<LetterTag> = emptySet(),
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun create(
            title: String,
            content: String,
            tags: Set<LetterTag> = emptySet(),
        ): Letter =
            Letter(
                title = title,
                content = LetterContent(content),
                tags = tags,
            )
    }
}
