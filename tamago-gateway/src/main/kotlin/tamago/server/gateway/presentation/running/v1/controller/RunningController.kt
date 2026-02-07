package tamago.server.gateway.presentation.running.v1.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.running.RunningFacade
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.running.v1.api.RunningApi
import tamago.server.gateway.presentation.running.v1.request.RunningRequest
import tamago.server.gateway.presentation.running.v1.request.toCommand
import tamago.server.gateway.presentation.running.v1.response.RunningFinishResponse

@RestController
class RunningController(
    private val runningFacade: RunningFacade,
) : RunningApi {

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/running")
    override fun saveRunningData(
        @CurrentUser user: User,
        @RequestBody @Valid request: RunningRequest,
    ): CustomResponse<RunningFinishResponse> {
        val result = runningFacade.saveRunningData(request.toCommand(user.id!!, user.weight))

        return CustomResponse.created(
            RunningFinishResponse(
                pace = result.pace,
                cadence = result.cadence,
                elapsedTime = result.elapsedTime,
                totalCalories = result.totalCalories,
                originXp = result.originXp,
                earnedXp = result.earnedXp,
                evolutionStages = result.evolutionStages.map { stage ->
                    RunningFinishResponse.EvolutionStageResponse(
                        stage = stage.stage,
                        monsterId = stage.monsterId,
                        evolutionXp = stage.evolutionXp,
                    )
                },
            ),
        )
    }
}
