package tamago.server.core.letter.application.service

import org.springframework.stereotype.Component
import tamago.server.core.letter.domain.enum.LetterTag

@Component
class LetterTagResolver {
    fun resolve(
        isFirstRun: Boolean,
        streakDays: Int,
        inactiveDays: Int,
    ): LetterTag =
        when {
            isFirstRun -> LetterTag.FIRST_START
            streakDays >= 10 -> LetterTag.STREAK_10_PLUS
            streakDays >= 6 -> LetterTag.STREAK_6_9
            streakDays >= 3 -> LetterTag.STREAK_3_5
            streakDays >= 2 -> LetterTag.STREAK_1_2
            inactiveDays >= 15 -> LetterTag.INACTIVE_15_PLUS
            inactiveDays >= 11 -> LetterTag.INACTIVE_11_14
            inactiveDays >= 8 -> LetterTag.INACTIVE_8_10
            inactiveDays >= 4 -> LetterTag.INACTIVE_4_7
            else -> LetterTag.INACTIVE_0_3
        }
}
