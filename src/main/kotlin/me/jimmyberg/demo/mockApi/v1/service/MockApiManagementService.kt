package me.jimmyberg.demo.mockApi.v1.service

import me.jimmyberg.demo.mockApi.v1.document.ModelInfo
import me.jimmyberg.demo.mockApi.v1.model.PostModelDataListRequest
import me.jimmyberg.demo.mockApi.v1.model.PostModelInfoRequest
import me.jimmyberg.demo.mockApi.v1.repository.ModelDataRepository
import me.jimmyberg.demo.mockApi.v1.repository.ModelInfoRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class MockApiManagementService(
    val modelDataService: ModelDataService,
    val modelInfoRepository: ModelInfoRepository,
    val modelDataRepository: ModelDataRepository
) {

    /**
     * Model info 단건 조회
     */
    fun findOneModelInfo(title: String) = modelInfoRepository.findByTitle(title)

    /**
     * Model info 저장
     */
    fun saveModelInfo(request: PostModelInfoRequest) = modelInfoRepository.save(ModelInfo.ofRequest(request))

    /**
     * Model data 다건 조회
     */
    fun getModelDataList(modelId: String, pageRequest: PageRequest) = modelDataRepository.findAllByModelId(modelId, pageRequest)

    /**
     * Model data 다건 저장
     */
    fun saveModelDataList(modelId: String, request: PostModelDataListRequest): List<Map<String, Any>>? {
        val modelInfo = modelInfoRepository.findById(modelId).orElse(null)
        return if (modelInfo != null) request.modelDataList.map { modelDataService.save(modelInfo.title, it) } else null
    }

}