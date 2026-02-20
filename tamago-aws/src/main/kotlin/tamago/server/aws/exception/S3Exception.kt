package tamago.server.aws.exception

import tamago.server.core.common.exception.BusinessException

class S3Exception: BusinessException(
    AwsExceptionCode.S3_ERROR
)
