package me.jimmyberg.demo.mockApi.beta

import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RequestMapping("/api/beta")
@RestController
class MockApiGetBetaController(
    val mockApiBetaService: MockApiBetaService
) {

    @GetMapping("/**")
    fun get(
        httpServletRequest: HttpServletRequest,
        @RequestParam request: Map<String, Any>
    ) = mockApiBetaService.getApi(httpServletRequest.requestURI, request)

    @PostMapping("/**")
    fun post(
        httpServletRequest: HttpServletRequest,
        @RequestBody request: Map<String, Any>
    ) = mockApiBetaService.getApi(httpServletRequest.requestURI, request)

}