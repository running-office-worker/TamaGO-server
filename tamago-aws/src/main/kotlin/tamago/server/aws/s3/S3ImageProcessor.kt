package tamago.server.aws.s3

import org.springframework.stereotype.Component
import tamago.server.aws.AwsProperties
import tamago.server.core.common.image.ImageFileConstructor
import tamago.server.core.common.image.ImageProcessor
import tamago.server.core.common.image.ImageInfo
import tamago.server.core.common.image.ImageUrl
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId

@Component
class S3ImageProcessor(
    private val awsS3Client: AwsS3Client,
    private val awsProperties: AwsProperties,
    private val imageFileConstructor: ImageFileConstructor,
) : ImageProcessor {
    override fun createUploadUrl(
        prefix: String,
        prefixId: Long,
        contentType: String,
        extension: String,
    ): ImageUrl {
        val imageFilePath = imageFileConstructor.imageFilePath(prefix, prefixId)
        val imageFileName = imageFileConstructor.imageFileName(extension)

        val presignedUrl =
            awsS3Client.generateUploadUrl(
                awsProperties.s3.bucket,
                imageFilePath,
                imageFileName,
                Duration.ofSeconds(30), // 만료 시간 최소화
                contentType,
            )

        return ImageUrl(
            uploadUrl = presignedUrl,
            previewUrl = generateGetUrl(imageFilePath, imageFileName),
            fileName = imageFileName,
        )
    }

    override fun getImageUrl(
        prefix: String,
        prefixId: Long,
        fileName: String?,
    ): List<ImageInfo> {
        val imageFilePath = imageFileConstructor.imageFilePath(prefix, prefixId)

        return fileName
            ?.let {
                listOf(presignedGet(imageFilePath, it)) // fileName이 있으면 특정 이미지 조회
            } ?: listPresignedGets(imageFilePath) // 아니면 해당 경로 아래 모든 이미지 탐색
    }

    private fun generateGetUrl(
        filePath: String,
        fileName: String,
        ttl: Duration = Duration.ofSeconds(60),
    ): String =
        awsS3Client.generateUrl(
            bucketName = awsProperties.s3.bucket,
            filePath = filePath,
            fileName = fileName,
            ttl = ttl,
        )

    private fun presignedGet(
        filePath: String,
        fileName: String,
        ttl: Duration = Duration.ofSeconds(60),
    ): ImageInfo {
        val url =
            awsS3Client.generateUrl(
                bucketName = awsProperties.s3.bucket,
                filePath = filePath,
                fileName = fileName,
                ttl = ttl,
            )
        val lastModified =
            awsS3Client.getObjectLastModified(
                bucketName = awsProperties.s3.bucket,
                key = "$filePath/$fileName",
            )
        return ImageInfo(
            url = url,
            uploadedAt = LocalDateTime.ofInstant(lastModified, ZoneId.of("Asia/Seoul")),
        )
    }

    private fun listPresignedGets(
        filePath: String,
        ttl: Duration = Duration.ofSeconds(30),
    ): List<ImageInfo> =
        awsS3Client
            .getBucketListObjects(
                bucketName = awsProperties.s3.bucket,
                filePath = filePath,
            ).contents()
            .orEmpty()
            .asSequence()
            .filterNot { it.key().endsWith("/") }
            .map { s3Object ->
                val fileName = s3Object.key().substringAfterLast("/")
                ImageInfo(
                    url =
                        awsS3Client.generateUrl(
                            bucketName = awsProperties.s3.bucket,
                            filePath = filePath,
                            fileName = fileName,
                            ttl = ttl,
                        ),
                    uploadedAt = LocalDateTime.ofInstant(s3Object.lastModified(), ZoneId.of("Asia/Seoul")),
                )
            }.toList()
}
