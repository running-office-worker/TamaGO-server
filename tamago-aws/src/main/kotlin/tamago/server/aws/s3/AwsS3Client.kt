package tamago.server.aws.s3

import org.springframework.stereotype.Component
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.HeadObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.S3Object
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration
import java.time.Instant

@Component
class AwsS3Client(
    private val s3Presigner: S3Presigner,
    private val s3Client: S3Client,
) {
    /**
     * S3 presigned GET URL 생성
     *
     * @param bucketName [String] 버킷명
     * @param filePath [String] 파일 경로
     * @param fileName [String] 파일명
     */
    fun generateUrl(
        bucketName: String,
        filePath: String,
        fileName: String,
        ttl: Duration,
    ): String {
        val getObjectRequest =
            GetObjectRequest
                .builder()
                .bucket(bucketName)
                .key("$filePath/$fileName")
                .responseContentType("application/octet-stream")
                .build()

        val getObjectPresignedUrlRequest =
            GetObjectPresignRequest
                .builder()
                .signatureDuration(ttl)
                .getObjectRequest(getObjectRequest)
                .build()

        val presignedGetObject =
            s3Presigner.presignGetObject(getObjectPresignedUrlRequest)

        return presignedGetObject.url().toExternalForm()
    }

    /**
     * S3 presigned PUT URL 생성
     */
    fun generateUploadUrl(
        bucketName: String,
        filePath: String,
        fileName: String,
        ttl: Duration,
        contentType: String = "image/jpeg",
    ): String {
        val putObjectRequest =
            PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key("$filePath/$fileName")
                .contentType(contentType)
                .build()

        val putObjectPresignedUrlRequest =
            PutObjectPresignRequest
                .builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(ttl)
                .build()

        val presignedRequest =
            s3Presigner.presignPutObject(putObjectPresignedUrlRequest)

        return presignedRequest.url().toExternalForm()
    }

    fun getBucketListObjects(
        bucketName: String,
        filePath: String,
    ): ListObjectsV2Response {
        var continuationToken: String? = null
        val listObjectsBuilder =
            ListObjectsV2Request
                .builder()
                .bucket(bucketName)
                .prefix(filePath)
                .maxKeys(1000)

        val allObjects = mutableListOf<S3Object>()

        do {
            val listObjectsRequest =
                listObjectsBuilder
                    .continuationToken(continuationToken)
                    .build()
            val listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest)
            allObjects.addAll(listObjectsResponse.contents())
            continuationToken = listObjectsResponse.nextContinuationToken()
        } while (listObjectsResponse.isTruncated)

        return ListObjectsV2Response
            .builder()
            .contents(allObjects)
            .keyCount(allObjects.size)
            .build()
    }

    fun getObjectLastModified(
        bucketName: String,
        key: String,
    ): Instant {
        val request =
            HeadObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build()

        return s3Client.headObject(request).lastModified()
    }

    fun putObject(
        bucketName: String,
        filePath: String,
        fileName: String,
        contentType: String,
        fileBytes: ByteArray,
    ) {
        val request =
            PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key("$filePath/$fileName")
                .contentType(contentType)
                .contentLength(fileBytes.size.toLong())
                .build()
        s3Client.putObject(request, RequestBody.fromBytes(fileBytes))
    }
}
