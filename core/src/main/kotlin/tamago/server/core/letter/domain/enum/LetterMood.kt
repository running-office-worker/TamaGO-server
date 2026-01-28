package tamago.server.core.letter.domain.enum

enum class LetterMood(
    val level: Int,
) {
    PROUD(5), // "연속 러닝, 목표 달성, 기록 갱신"
    PRAISE(4),//  "오늘 달림"
    ENCOURAGE(3), // "어제 달렸으니 오늘도 달리자"
    WORRY(2), // "2~3일 안 달림"
    SAD(1), // "일주일 이상 안 달림"
    ;
}
