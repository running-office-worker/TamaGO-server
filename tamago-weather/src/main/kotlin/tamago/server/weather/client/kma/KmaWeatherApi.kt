package tamago.server.weather.client.kma

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "kma-weather-api",
    url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0",
)
internal interface KmaWeatherApi {
    @GetMapping("/getUltraSrtNcst")
    fun getCurrentConditions(
        @RequestParam("serviceKey") serviceKey: String,
        @RequestParam("pageNo") pageNo: Int,
        @RequestParam("numOfRows") numOfRows: Int,
        @RequestParam("dataType") dataType: String,
        @RequestParam("base_date") baseDate: String,
        @RequestParam("base_time") baseTime: String,
        @RequestParam("nx") nx: Int,
        @RequestParam("ny") ny: Int,
    ): KmaWeatherResponse
}
