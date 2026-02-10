package tamago.server.core.common.image

data class S3ImageUrl(
    val presignedUrl: String,
    val presignedGetUrl: String,
)
