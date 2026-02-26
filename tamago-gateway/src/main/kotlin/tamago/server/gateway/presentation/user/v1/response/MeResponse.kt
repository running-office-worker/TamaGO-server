package tamago.server.gateway.presentation.user.v1.response

import io.swagger.v3.oas.annotations.media.Schema
import tamago.server.core.user.domain.aggregate.User
import tamago.server.core.user.domain.enum.AuthProvider
import tamago.server.core.user.domain.enum.UserRole
import java.time.LocalDateTime

data class MeResponse(
    @field:Schema(description = "유저 ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    val id: Long,
    @field:Schema(description = "닉네임", example = "타마고")
    val nickname: String?,
    @field:Schema(description = "유저 역할", example = "USER", requiredMode = Schema.RequiredMode.REQUIRED)
    val role: UserRole,
    @field:Schema(description = "몸무게 (kg)", example = "70.0", requiredMode = Schema.RequiredMode.REQUIRED)
    val weight: Double,
    @field:Schema(description = "러닝 데이터", requiredMode = Schema.RequiredMode.REQUIRED)
    val runningData: RunningDataDto,
    @field:Schema(description = "인증 정보 목록", requiredMode = Schema.RequiredMode.REQUIRED)
    val auths: List<AuthDto>,
    @field:Schema(description = "마지막 로그인 일시")
    val lastLoginAt: LocalDateTime?,
    @field:Schema(description = "가입 일시")
    val createdAt: LocalDateTime?,
    @field:Schema(description = "수정 일시")
    val updatedAt: LocalDateTime?,
) {
    data class RunningDataDto(
        @field:Schema(description = "하루 러닝 목표 (km)", example = "5")
        val goalKilo: Int?,
        @field:Schema(description = "누적 러닝 거리 (km)", example = "42.5")
        val totalKilo: Double?,
    )

    data class AuthDto(
        @field:Schema(description = "이메일", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        val email: String,
        @field:Schema(description = "인증 제공자", example = "KAKAO", requiredMode = Schema.RequiredMode.REQUIRED)
        val provider: AuthProvider,
    )

    companion object {
        fun from(user: User): MeResponse =
            MeResponse(
                id = user.id!!.value,
                nickname = user.nickname,
                role = user.role,
                weight = user.weight,
                runningData =
                    RunningDataDto(
                        goalKilo = user.runningData.goalKilo,
                        totalKilo = user.runningData.totalKilo,
                    ),
                auths =
                    user.auths.map { auth ->
                        AuthDto(
                            email = auth.email,
                            provider = auth.provider,
                        )
                    },
                lastLoginAt = user.lastLoginAt,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
            )
    }
}
