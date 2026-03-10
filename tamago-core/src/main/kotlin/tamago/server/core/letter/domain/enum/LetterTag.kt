package tamago.server.core.letter.domain.enum

enum class LetterTag {
    FIRST_START,
    INACTIVE_0_3,
    INACTIVE_4_7,
    INACTIVE_8_10,
    INACTIVE_11_14,
    INACTIVE_15_PLUS,
    STREAK_1_2,
    STREAK_3_5,
    STREAK_6_9,
    STREAK_10_PLUS,
    ;

    companion object {
        fun resolve(
            isFirstRun: Boolean,
            streakDays: Int,
            inactiveDays: Int,
        ): LetterTag =
            when {
                isFirstRun -> FIRST_START
                streakDays >= 10 -> STREAK_10_PLUS
                streakDays >= 6 -> STREAK_6_9
                streakDays >= 3 -> STREAK_3_5
                streakDays >= 1 -> STREAK_1_2
                inactiveDays >= 15 -> INACTIVE_15_PLUS
                inactiveDays >= 11 -> INACTIVE_11_14
                inactiveDays >= 8 -> INACTIVE_8_10
                inactiveDays >= 4 -> INACTIVE_4_7
                else -> INACTIVE_0_3
            }
    }
}
