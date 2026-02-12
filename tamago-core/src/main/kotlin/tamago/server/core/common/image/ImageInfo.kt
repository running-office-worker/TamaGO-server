package tamago.server.core.common.image

import java.time.LocalDateTime

data class ImageInfo(
    val url: String,
    val uploadedAt: LocalDateTime,
)
