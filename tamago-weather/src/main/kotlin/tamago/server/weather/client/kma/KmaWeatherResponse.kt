package tamago.server.weather.client.kma

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaWeatherResponse(
    val response: KmaResponse,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaResponse(
    val header: KmaResponseHeader,
    val body: KmaResponseBody?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaResponseHeader(
    val resultCode: String,
    val resultMsg: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaResponseBody(
    val dataType: String?,
    val items: KmaItems?,
    val pageNo: Int?,
    val numOfRows: Int?,
    val totalCount: Int?,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaItems(
    val item: List<KmaWeatherItem> = emptyList(),
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class KmaWeatherItem(
    val baseDate: String?,
    val baseTime: String?,
    val category: String?,
    val obsrValue: String?,
    val nx: Int?,
    val ny: Int?,
)
