package me.jimmyberg.demo.mockApi.beta

import org.springframework.web.bind.annotation.*

@RequestMapping("/api/management")
@RestController
class MockApiManagementBetaController(
    val mockApiBetaService: MockApiBetaService
) {

    @PostMapping
    fun post(@RequestBody request: List<MockApiCollectionRequest>) = mockApiBetaService.postApi(request)

    @GetMapping
    fun get(@RequestParam(required = false) url: String?) = mockApiBetaService.getAllApi(url)

    @PutMapping
    fun put(@RequestBody request: MockApiCollectionRequest) = mockApiBetaService.putApi(request)

    @DeleteMapping
    fun delete(@RequestParam id: String) = mockApiBetaService.deleteApi(id)

}