package tamago.server.aws.s3.exception

import tamago.server.aws.exception.AwsExceptionCode
import tamago.server.core.common.exception.BusinessException

class S3Exception(cause: Throwable) : BusinessException(AwsExceptionCode.S3_ERROR) {
    init {
        initCause(cause)
    }
}
