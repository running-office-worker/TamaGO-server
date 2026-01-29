package tamago.server.core.letter.application.exception

import tamago.server.core.common.exception.BusinessException

class LetterAccessDeniedException : BusinessException(
    LetterExceptionCode.LETTER_ACCESS_DENIED,
)
