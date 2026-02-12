package tamago.server.core.common.image

interface ImageProcessor {
    /**
     * `@param` prefix [String] 도메인
     * `@param` prefixId [Long] 도메인 ID
     * `@param` contentType [String] 콘텐츠 타입 (기본값: "image/jpeg")
     * `@param` extension [String] 파일 확장자 (기본값: "jpeg")
     */
    fun createUploadUrl(
        prefix: String,
        prefixId: Long,
        contentType: String = "image/jpeg",
        extension: String = "jpeg",
    ): ImageUrl

    /**
     * @param prefix [String] 도메인
     * @param prefixId [Long] 도메인 ID
     * @param fileName [String] 파일명 (null일 경우 해당 경로 아래 모든 이미지 조회)
     */
    fun getImageUrl(
        prefix: String,
        prefixId: Long,
        fileName: String?,
    ): List<ImageInfo>
}
