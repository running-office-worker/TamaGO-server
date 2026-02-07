package tamago.server.core.running.domain.util

import java.time.Duration
import java.time.LocalDateTime

object RunningCalculator {

    fun calculatePace(distanceKm: Double, startedAt: LocalDateTime, finishedAt: LocalDateTime): Double {
        val elapsedSeconds = Duration.between(startedAt, finishedAt).seconds
        return if (distanceKm > 0) elapsedSeconds.toDouble() / distanceKm else 0.0
    }

    fun calculateCadence(distanceKm: Double, startedAt: LocalDateTime, finishedAt: LocalDateTime): Int {
        val elapsedSeconds = Duration.between(startedAt, finishedAt).seconds
        val hours = elapsedSeconds / 3600.0
        val speedKmh = if (hours > 0) distanceKm / hours else 0.0
        val stepsPerMinute = when {
            speedKmh < 8.0 -> 155
            speedKmh < 10.0 -> 165
            speedKmh < 12.0 -> 175
            else -> 185
        }
        return stepsPerMinute
    }

    fun calculateCalories(distanceKm: Double, weight: Double, startedAt: LocalDateTime, finishedAt: LocalDateTime): Int {
        val elapsedSeconds = Duration.between(startedAt, finishedAt).seconds
        val hours = elapsedSeconds / 3600.0
        val speedKmh = if (hours > 0) distanceKm / hours else 0.0
        val met = when {
            speedKmh < 8.0 -> 8.3
            speedKmh < 10.0 -> 9.8
            speedKmh < 12.0 -> 11.0
            else -> 12.8
        }
        return (met * weight * hours).toInt()
    }
}
