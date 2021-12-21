package me.jimmyberg.demo.mockApi.v1.controller

import me.jimmyberg.demo.mockApi.v1.service.MockApiService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RequestMapping("/api")
@RestController
class MockApiController(
    val mockApiService: MockApiService
) {

    @GetMapping("/**")
    fun get(httpServletRequest: HttpServletRequest, @RequestParam request: Map<String, Any>) = mockApiService.request(httpServletRequest, request)

    @PostMapping("/**")
    fun post(httpServletRequest: HttpServletRequest, @RequestBody request: Map<String, Any>) = mockApiService.request(httpServletRequest, request)

}