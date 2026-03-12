package tamago.server.core.letter.application.exception

import tamago.server.core.common.exception.BusinessException

class LetterNotFoundException :
    BusinessException(
        LetterExceptionCode.LETTER_NOT_FOUND,
    )
