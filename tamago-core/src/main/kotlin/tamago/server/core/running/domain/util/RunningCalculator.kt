package tamago.server.core.running.domain.util

import tamago.server.core.running.application.exception.InvalidRunningDataException
import java.time.Duration
import java.time.LocalDateTime

object RunningCalculator {
    private fun elapsedSeconds(
        startedAt: LocalDateTime,
        finishedAt: LocalDateTime,
    ): Long {
        val seconds = Duration.between(startedAt, finishedAt).seconds
        if (seconds <= 0) throw InvalidRunningDataException()
        return seconds
    }

    private fun validateDistance(distanceKm: Double) {
        if (distanceKm < 0) throw InvalidRunningDataException()
    }

    fun calculatePace(
        distanceKm: Double,
        startedAt: LocalDateTime,
        finishedAt: LocalDateTime,
    ): Double {
        validateDistance(distanceKm)
        if (distanceKm == 0.0) return 0.0 // 0 나눗셈으로 인한 sql 바인딩 에러 방지
        return elapsedSeconds(startedAt, finishedAt).toDouble() / distanceKm
    }

    fun calculateCadence(
        distanceKm: Double,
        startedAt: LocalDateTime,
        finishedAt: LocalDateTime,
    ): Int {
        validateDistance(distanceKm)
        if (distanceKm == 0.0) return 0 // 0 나눗셈으로 인한 sql 바인딩 에러 방지
        val hours = elapsedSeconds(startedAt, finishedAt) / 3600.0
        val speedKmh = distanceKm / hours
        return when {
            speedKmh < 8.0 -> 155
            speedKmh < 10.0 -> 165
            speedKmh < 12.0 -> 175
            else -> 185
        }
    }

    fun calculateCalories(
        distanceKm: Double,
        weight: Double,
        startedAt: LocalDateTime,
        finishedAt: LocalDateTime,
    ): Int {
        validateDistance(distanceKm)
        if (distanceKm == 0.0) return 0
        val hours = elapsedSeconds(startedAt, finishedAt) / 3600.0
        val speedKmh = distanceKm / hours
        val met =
            when {
                speedKmh < 8.0 -> 8.3
                speedKmh < 10.0 -> 9.8
                speedKmh < 12.0 -> 11.0
                else -> 12.8
            }
        return (met * weight * hours).toInt()
    }
}
