package tamago.server.gateway.presentation.letter.v1.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import tamago.server.core.letter.domain.vo.UserLetterId
import tamago.server.core.user.domain.aggregate.User
import tamago.server.gateway.common.response.CustomResponse
import tamago.server.gateway.presentation.letter.v1.response.LetterResponse

@Tag(name = "Letter API", description = "타마고의 편지 API")
interface LetterApi {
    @Operation(summary = "편지 수신함 조회", description = "가장 최근 받은 편지 한 개를 조회합니다.")
    fun readLetterInbox(user: User): CustomResponse<LetterResponse>

    @Operation(summary = "편지 수신 확인", description = "가장 최근 받은 편지를 읽음 처리합니다.")
    fun markAsReadLetter(user: User, letterId: UserLetterId): CustomResponse<Void>
}
