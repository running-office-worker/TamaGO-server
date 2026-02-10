package tamago.server.gateway.presentation.monster.v1.controller

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.monster.MonsterFacade
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.api.InitMonsterApi
import tamago.server.gateway.presentation.monster.v1.response.InitMonsterResponse

@Profile("local", "dev")
@RestController
class InitMonsterController(
    private val monsterFacade: MonsterFacade,
) : InitMonsterApi {

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/api/v1/monsters/init")
    override fun initRandomMonster(
        @CurrentUser user: User,
    ): CustomResponse<InitMonsterResponse> {
        val result = monsterFacade.initRandomMonster(user.id!!)

        return CustomResponse.created(
            InitMonsterResponse(
                ownedMonsterId = result.ownedMonsterId,
                monsterId = result.monsterId,
                nickname = result.nickname,
                status = result.status,
            ),
        )
    }
}
