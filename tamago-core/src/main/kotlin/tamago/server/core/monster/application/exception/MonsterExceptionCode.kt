package tamago.server.core.monster.application.exception

import org.springframework.http.HttpStatus
import tamago.server.core.common.exception.ExceptionCode

enum class MonsterExceptionCode(
    @JvmField val status: HttpStatus,
    @JvmField val code: String,
    @JvmField val message: String,
) : ExceptionCode {
    MONSTER_NOT_FOUND(HttpStatus.NOT_FOUND, "MONSTER_4040", "몬스터를 찾을 수 없습니다."),
    OWNED_MONSTER_NOT_FOUND(HttpStatus.NOT_FOUND, "MONSTER_4041", "소유한 몬스터를 찾을 수 없습니다."),
    MONSTER_ASSET_ALREADY_EXISTS(HttpStatus.CONFLICT, "MONSTER_4090", "해당 몬스터의 에셋이 이미 존재합니다."),
    MONSTER_ALREADY_OWNED(HttpStatus.CONFLICT, "MONSTER_4091", "이미 소유한 몬스터입니다."),
    ;

    override fun getStatus(): HttpStatus = status

    override fun getCode(): String = code

    override fun getMessage(): String = message
}
