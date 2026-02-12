package tamago.server.gateway.presentation.monster.v1.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.monster.MonsterFacade
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.api.MonsterApi
import tamago.server.gateway.presentation.monster.v1.response.MonsterDexResponse

@RestController
class MonsterController(
    private val monsterFacade: MonsterFacade,
) : MonsterApi {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/dex")
    override fun getMonsterDex(
        @CurrentUser user: User,
    ): CustomResponse<List<MonsterDexResponse>> {
        val result = monsterFacade.getMonsterDex(user.id!!)
        return CustomResponse.ok(result.map { MonsterDexResponse.from(it) })
    }
}
