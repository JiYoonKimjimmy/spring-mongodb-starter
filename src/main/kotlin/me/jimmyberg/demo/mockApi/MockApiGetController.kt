package me.jimmyberg.demo.mockApi

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@Api(description = "Mock API Request Test", tags = ["Mock API 요청"])
@RequestMapping("/api")
@RestController
class MockApiGetController(
    val mockApiService: MockApiService
) {

    @GetMapping("/**")
    fun get(
        httpServletRequest: HttpServletRequest,
        @RequestParam request: Map<String, Any>
    ) = mockApiService.getApi(httpServletRequest.requestURI, request)

    @PostMapping("/**")
    fun post(
        httpServletRequest: HttpServletRequest,
        @RequestBody request: Map<String, Any>
    ) = mockApiService.getApi(httpServletRequest.requestURI, request)

}