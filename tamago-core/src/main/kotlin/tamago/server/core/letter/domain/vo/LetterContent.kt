package tamago.server.core.letter.domain.vo

@JvmInline
value class LetterContent(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "Letter content must not be blank" }
    }

    override fun toString(): String = value
}
