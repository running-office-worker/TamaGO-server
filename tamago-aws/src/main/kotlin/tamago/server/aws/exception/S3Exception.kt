package tamago.server.aws.exception

import tamago.server.core.common.exception.BusinessException

class S3Exception(cause: Throwable? = null) : BusinessException(AwsExceptionCode.S3_ERROR) {
    init {
        cause?.let { initCause(it) }
    }
}
