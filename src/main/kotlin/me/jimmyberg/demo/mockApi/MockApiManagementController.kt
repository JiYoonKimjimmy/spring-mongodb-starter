package me.jimmyberg.demo.mockApi

import io.swagger.annotations.Api
import org.springframework.web.bind.annotation.*

@Api(description = "Mock API Management", tags = ["Mock API 관리"])
@RequestMapping("/api/management")
@RestController
class MockApiManagementController(
    val mockApiService: MockApiService
) {

    @PostMapping
    fun post(@RequestBody request: List<MockApiCollectionRequest>) = mockApiService.postApi(request)

    @GetMapping
    fun get(@RequestParam(required = false) url: String?) = mockApiService.getAllApi(url)

    @PutMapping
    fun put(@RequestBody request: MockApiCollectionRequest) = mockApiService.putApi(request)

    @DeleteMapping
    fun delete(@RequestParam id: String) = mockApiService.deleteApi(id)

}