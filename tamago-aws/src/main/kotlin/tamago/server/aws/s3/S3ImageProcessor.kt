package tamago.server.aws.s3

import org.springframework.stereotype.Component
import software.amazon.awssdk.core.exception.SdkException
import tamago.server.aws.AwsProperties
import tamago.server.aws.exception.S3Exception
import tamago.server.core.common.image.ImageFileConstructor
import tamago.server.core.common.image.ImageInfo
import tamago.server.core.common.image.ImageProcessor
import tamago.server.core.common.image.ImageUrl
import tamago.server.core.common.image.UploadedImage
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
        prefixPath: String,
        contentType: String,
        extension: String,
    ): ImageUrl {
        try {
            val imageFilePath = imageFileConstructor.imageFilePath(prefix, prefixPath)
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
        } catch (e: SdkException) {
            throw S3Exception(e)
        }
    }

    override fun getImageUrl(
        prefix: String,
        prefixPath: String,
        fileName: String?,
    ): List<ImageInfo> {
        try {
            val imageFilePath = imageFileConstructor.imageFilePath(prefix, prefixPath)

            return fileName
                ?.let {
                    listOf(presignedGet(imageFilePath, it)) // fileName이 있으면 특정 이미지 조회
                } ?: listPresignedGets(imageFilePath) // 아니면 해당 경로 아래 모든 이미지 탐색
        } catch (e: SdkException) {
            throw S3Exception(e)
        }
    }

    override fun getImageUrl(assetKey: String): String {
        val filePath = assetKey.substringBeforeLast("/")
        val fileName = assetKey.substringAfterLast("/")
        return generateGetUrl(filePath, fileName)
    }

    override fun uploadFile(
        prefix: String,
        prefixPath: String,
        contentType: String,
        extension: String,
        fileBytes: ByteArray,
        fileName: String,
    ): UploadedImage {
        try {
            val filePath = imageFileConstructor.imageFilePath(prefix, prefixPath)

            awsS3Client.putObject(awsProperties.s3.bucket, filePath, fileName, contentType, fileBytes)

            return UploadedImage(
                previewUrl = generateGetUrl(filePath, fileName),
                fileName = fileName,
            )
        } catch (e: SdkException) {
            throw S3Exception(e)
        }
    }

    override fun deleteFile(
        prefix: String,
        prefixPath: String,
        fileName: String,
    ) {
        try {
            val filePath = imageFileConstructor.imageFilePath(prefix, prefixPath)
            awsS3Client.deleteObject(awsProperties.s3.bucket, "$filePath/$fileName")
        } catch (e: SdkException) {
            throw S3Exception(e)
        }
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
