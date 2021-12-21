package me.jimmyberg.demo.mockApi.v1.controller

import me.jimmyberg.demo.mockApi.v1.service.MockApiManagementService
import me.jimmyberg.demo.mockApi.v1.model.PostModelDataListRequest
import me.jimmyberg.demo.mockApi.v1.model.PostModelInfoRequest
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/management/v1")
@RestController
class MockApiManagementController(
    val mockApiManagementService: MockApiManagementService
) {

    @GetMapping("/model-info/{title}")
    fun getModelInfo(@PathVariable title: String) = mockApiManagementService.findOneModelInfo(title)

    @PostMapping("/model-info")
    fun postModelInfo(@RequestBody request: PostModelInfoRequest) = mockApiManagementService.saveModelInfo(request)

    @GetMapping("/model-data/{modelId}")
    fun getModelData(
        @PathVariable modelId: String,
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") pageSize: Int
    ) = mockApiManagementService.getModelDataList(modelId, PageRequest.of(page, pageSize))

    @PostMapping("/model-data/{modelId}")
    fun postModelDataList(
        @PathVariable modelId: String,
        @RequestBody request: PostModelDataListRequest
    ) = mockApiManagementService.saveModelDataList(modelId, request)

}