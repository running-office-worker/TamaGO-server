package tamago.server.core.running.application.service

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Service
import tamago.server.core.running.RunningCommandUseCase
import tamago.server.core.running.application.exception.InvalidRunningDataException
import tamago.server.core.running.domain.aggregate.Running
import tamago.server.core.running.domain.aggregate.RunningRoute
import tamago.server.core.running.domain.port.inbound.command.SaveRunningCommandDto
import tamago.server.core.running.domain.port.outbound.RunningPersistencePort
import tamago.server.core.running.domain.port.outbound.RunningRoutePersistencePort
import tamago.server.core.running.domain.util.RunningCalculator.calculateCadence
import tamago.server.core.running.domain.util.RunningCalculator.calculateCalories
import tamago.server.core.running.domain.util.RunningCalculator.calculatePace
import java.time.Duration

@Service
class RunningCommandService(
    private val runningPersistencePort: RunningPersistencePort,
    private val runningRoutePersistencePort: RunningRoutePersistencePort,
) : RunningCommandUseCase {

    private val geometryFactory = GeometryFactory(
        PrecisionModel(),
        4326
    )

    fun save(command: SaveRunningCommandDto): Running {
        if (command.distance < 0 || !command.finishedAt.isAfter(command.startedAt)) {
            throw InvalidRunningDataException()
        }

        val pace = calculatePace(command.distance, command.startedAt, command.finishedAt)
        val cadence = calculateCadence(command.distance, command.startedAt, command.finishedAt)
        val calories = calculateCalories(command.distance, command.weight, command.startedAt, command.finishedAt)
        val elapsedTime = Duration.between(command.startedAt, command.finishedAt).seconds.toInt()

        val running = Running.create(
            userId = command.userId,
            ownedMonsterId = command.ownedMonsterId,
            pace = pace,
            cadence = cadence,
            calories = calories,
            distance = command.distance,
            elevationGain = command.elevationGain,
            heartbeat = command.heartbeat,
            elapsedTime = elapsedTime,
            startedAt = command.startedAt,
            finishedAt = command.finishedAt,
        )

        val savedRunning = runningPersistencePort.save(running)
        saveRunningRoute(savedRunning, command)
        return savedRunning
    }

    private fun saveRunningRoute(savedRunning: Running, command: SaveRunningCommandDto) {
        // 각 waypoint을 JTS Coordinate로 변환하여 배열 생성
        val waypoints = command.waypoints
        val coordinates = waypoints.map { Coordinate(it.longitude, it.latitude) }.toTypedArray()

        // waypoint이 2개 이상인 경우에만 LineString 생성
        val route = if (coordinates.size >= 2) geometryFactory.createLineString(coordinates) else null
        val startPoint = coordinates.firstOrNull()?.let { geometryFactory.createPoint(it) }
        val endPoint = coordinates.lastOrNull()?.let { geometryFactory.createPoint(it) }

        val runningRoute = RunningRoute.create(
            runningId = savedRunning.id!!,
            route = route,
            startPoint = startPoint,
            endPoint = endPoint,
        )
        runningRoutePersistencePort.save(runningRoute)
    }
}
