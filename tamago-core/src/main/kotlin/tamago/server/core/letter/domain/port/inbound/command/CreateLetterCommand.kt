package tamago.server.core.letter.domain.port.inbound.command

import tamago.server.core.letter.domain.enum.LetterTag

data class CreateLetterCommand(
    val title: String,
    val content: String,
    val tags: Set<LetterTag> = emptySet(),
)
