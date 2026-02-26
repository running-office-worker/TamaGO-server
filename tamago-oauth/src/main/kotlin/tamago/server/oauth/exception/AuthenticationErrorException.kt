package tamago.server.oauth.exception

import tamago.server.core.common.exception.BusinessException

class AuthenticationErrorException :
    BusinessException(
        OAuthExceptionCode.AUTHENTICATION_ERROR,
    )
