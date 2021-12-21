package me.jimmyberg.demo.mockApi.v1.repository

import me.jimmyberg.demo.mockApi.v1.document.ModelData
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.repository.MongoRepository

interface ModelDataRepository : MongoRepository<ModelData, String> {

    fun findAllByModelId(modelId: String?, pageable: PageRequest): Page<ModelData>

}