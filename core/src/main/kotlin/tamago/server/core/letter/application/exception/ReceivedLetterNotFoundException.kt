package tamago.server.core.letter.application.exception

import tamago.server.core.common.exception.BusinessException

class ReceivedLetterNotFoundException : BusinessException(
    LetterExceptionCode.RECEIVED_LETTER_NOT_FOUND,
)
