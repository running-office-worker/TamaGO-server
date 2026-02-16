package tamago.server.core.letter.application.exception

import tamago.server.core.common.exception.BusinessException

class UserLetterNotFoundException : BusinessException(
    LetterExceptionCode.USER_LETTER_NOT_FOUND,
)
