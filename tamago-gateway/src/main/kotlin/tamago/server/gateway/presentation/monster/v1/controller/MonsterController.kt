package tamago.server.gateway.presentation.monster.v1.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import tamago.server.core.monster.MonsterFacade
import tamago.server.core.monster.MonsterQueryUseCase
import tamago.server.core.monster.domain.vo.OwnedMonsterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.annotation.CurrentUser
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.monster.v1.api.MonsterApi
import tamago.server.gateway.presentation.monster.v1.response.MonsterDexResponse
import tamago.server.gateway.presentation.monster.v1.response.UnlockedMonsterResponse

@RestController
class MonsterController(
    private val monsterFacade: MonsterFacade,
    private val monsterQueryUseCase: MonsterQueryUseCase,
) : MonsterApi {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/dex")
    override fun getMonsterDex(
        @CurrentUser user: User,
    ): CustomResponse<List<MonsterDexResponse>> {
        val result = monsterFacade.getMonsterDex(user.id!!)
        return CustomResponse.ok(result.map { MonsterDexResponse.from(it) })
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/v1/monsters/unlocked")
    override fun getUnlockedMonsters(
        @CurrentUser user: User,
    ): CustomResponse<List<UnlockedMonsterResponse>> {
        val unlockedMonsters = monsterQueryUseCase.getUnlockedMonsters(user.id!!)
        return CustomResponse.ok(unlockedMonsters.map { UnlockedMonsterResponse.from(it) })
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/api/v1/monsters/unlocked/{ownedMonsterId}")
    override fun ownMonster(
        @CurrentUser user: User,
        @PathVariable ownedMonsterId: Long,
    ): CustomResponse<Unit> {
        monsterFacade.ownMonster(OwnedMonsterId(ownedMonsterId))
        return CustomResponse.ok()
    }
}
